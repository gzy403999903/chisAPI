package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.bean.LossRecord;
import com.chisapp.modules.inventory.dao.LossRecordMapper;
import com.chisapp.modules.inventory.service.InventoryService;
import com.chisapp.modules.inventory.service.LossRecordService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Tandy
 * @Date: 2020/1/4 23:06
 * @Version 1.0
 */
@Service
public class LossRecordServiceImpl implements LossRecordService {

    private LossRecordMapper lossRecordMapper;
    @Autowired
    public void setLossRecordMapper(LossRecordMapper lossRecordMapper) {
        this.lossRecordMapper = lossRecordMapper;
    }

    private InventoryService inventoryService;
    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void saveList(List<LossRecord> lossRecordList) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        String lsh = KeyUtils.getLSH(user.getId()); // 获取流水号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        LossRecordMapper mapper = batchSqlSession.getMapper(LossRecordMapper.class);
        try {
            for (LossRecord lossRecord: lossRecordList) {
                lossRecord.setLsh(lsh);
                lossRecord.setMxh(String.valueOf(mxh++));
                lossRecord.setSysClinicId(user.getSysClinicId());
                lossRecord.setCreatorId(user.getId());
                lossRecord.setCreationDate(new Date());
                lossRecord.setApproveState(ApproveStateEnum.PENDING.getIndex()); // 设置为待审批状态

                mapper.insert(lossRecord);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateApproveState(Integer approverId, Date approveDate, Byte approveState, String lsh) {
        lossRecordMapper.updateApproveState(approverId, approveDate, approveState, lsh);
    }

    @Override
    public void unapproved(String lsh) {
        // 获取对应的明细
        List<LossRecord> lossRecordList = this.parseToObjectList(this.getByLsh(lsh));
        // 检查明细是否全部在给定状态
        Boolean b = this.examineApproveState(lossRecordList, ApproveStateEnum.PENDING.getIndex());
        if (!b) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }
        // 执行更新
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        this.updateApproveState(user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(), lsh);
    }

    @Override
    public void approved(String lsh) {
        // 获取对应的明细
        List<LossRecord> lossRecordList = this.parseToObjectList(this.getByLsh(lsh));
        // 检查明细是否全部在给定状态
        Boolean b = this.examineApproveState(lossRecordList, ApproveStateEnum.PENDING.getIndex());
        if (!b) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }
        // 执行更新
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        this.updateApproveState(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(), lsh);

        // 扣减对应库存
        List<Integer> inventoryIdList = new ArrayList<>();
        Map<Integer, LossRecord> lossRecordMap = new HashMap<>();
        for (LossRecord lossRecord : lossRecordList) {
            int iymInventoryId = lossRecord.getIymInventoryId();
            // 获取要查询的库存ID
            inventoryIdList.add(iymInventoryId);
            // 封装 key=库存ID  value=SelfUsedRecord
            lossRecordMap.put(iymInventoryId, lossRecord);
        }

        // 获取对应的库存
        List<Inventory> inventoryList = this.inventoryService.getByIdList(inventoryIdList);

        // 将库存数量改为领用出库的数量并进行更新
        for (Inventory inventory : inventoryList) {
            // 获取对应库存ID的领用出库数量
            int quantity = lossRecordMap.get(inventory.getId()).getQuantity();
            // 判断数量是否足够
            if (inventory.getQuantity() < quantity) {
                throw new RuntimeException("商品编码:【" + inventory.getGsmGoodsOid() + "】" +
                        " 商品名称:【" + inventory.getGsmGoodsName() + "】" +
                        " 批号:【" + inventory.getPh() + "】" +
                        " 批次号:【" + inventory.getPch() + "】" +
                        " 库存数量不足");
            }
            // 更新库存数量 (更新时原数量会减去出库数量)
            inventory.setQuantity(quantity);
        }
        // 执行库存数量更新
        inventoryService.updateQuantityByList(inventoryList);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer sysClinicId, String[] creationDate, String lsh, String sysClinicName, Byte approveState) {
        return lossRecordMapper.selectByCriteria(sysClinicId, creationDate, lsh, sysClinicName, approveState);
    }

    @Override
    public List<Map<String, Object>> getClinicGroupListByCriteria(Integer sysClinicId, String[] creationDate, String lsh, Byte approveState) {
        return lossRecordMapper.selectClinicGroupListByCriteria(sysClinicId, creationDate, lsh, approveState);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return lossRecordMapper.selectByLsh(lsh);
    }

}
