package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.bean.InventoryAllot;
import com.chisapp.modules.inventory.dao.InventoryAllotMapper;
import com.chisapp.modules.inventory.service.InventoryAllotService;
import com.chisapp.modules.inventory.service.InventoryService;
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
 * @Date: 2019/10/15 17:55
 * @Version 1.0
 */
@Service
public class InventoryAllotServiceImpl implements InventoryAllotService {

    private InventoryAllotMapper inventoryAllotMapper;
    @Autowired
    public void setInventoryAllotMapper(InventoryAllotMapper inventoryAllotMapper) {
        this.inventoryAllotMapper = inventoryAllotMapper;
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
    public void save(List<InventoryAllot> allotList) {
        // 获取用户信息、 流水号、 明细号
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String lsh = KeyUtils.getLSH(user.getId());
        int mxh = 1;

        // 提交保存仓库调拨记录
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        InventoryAllotMapper mapper = batchSqlSession.getMapper(InventoryAllotMapper.class);
        try {
            for (InventoryAllot allot : allotList) {
                allot.setLsh(lsh);
                allot.setMxh(String.valueOf(mxh++));
                allot.setSysClinicId(user.getSysClinicId());
                allot.setCreatorId(user.getId());
                allot.setCreationDate(new Date());
                // 直接设置为审核通过状态
                allot.setApproveState(ApproveStateEnum.APPROVED.getIndex());

                mapper.insert(allot);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }

        // 变更仓库操作
        this.updateIymInventoryTypeId(allotList);

    }

    // *****************************************************************************************************************
    // 放弃审核, 直接保存即可审核通过, 同时不再拆分调拨数量, 将操作数据行直接更改为目标仓库类型 Date: 2020-03-03, By: Tandy
    // *****************************************************************************************************************
    private void updateIymInventoryTypeId (List<InventoryAllot> allotList) {
        List<Inventory> inventoryList = new ArrayList<>();
        for (InventoryAllot inventoryAllot : allotList) {
            Inventory inventory = new Inventory();
            inventory.setId(inventoryAllot.getIymInventoryId());
            inventory.setIymInventoryTypeId(inventoryAllot.getToIymInventoryTypeId());

            inventoryList.add(inventory);
        }
        this.inventoryService.updateIymInventoryTypeIdByList(inventoryList);

    }

    @Override
    public void unapproved(String lsh) {
        List<InventoryAllot> inventoryAllotList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventoryAllotList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        inventoryAllotMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(), lsh);
    }

    @Override
    public void approved(String lsh) {
        List<InventoryAllot> inventoryAllotList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(inventoryAllotList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 更新单据为通过状态
        inventoryAllotMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(), lsh);

        // 获取要进行调拨的库存
        List<Integer> inventoryIdList = new ArrayList<>();
        Map<Integer, InventoryAllot> inventoryAllotMap = new HashMap<>();
        for (InventoryAllot inventoryAllot : inventoryAllotList) {
            inventoryIdList.add(inventoryAllot.getIymInventoryId()); // 要进行调拨的库存ID集合
            inventoryAllotMap.put(inventoryAllot.getIymInventoryId(), inventoryAllot);  // 封装可以根据库存ID 获取对应调拨对象的 Map
        }
        // 获取调拨库存集合
        List<Inventory> inventoryList = inventoryService.getByIdList(inventoryIdList);

        // 调出操作(减库存)
        for (Inventory inventory : inventoryList) {
            int inventoryId = inventory.getId();
            int allotQuantity = inventoryAllotMap.get(inventoryId).getQuantity(); // 获取调拨数量
            // 判断库存是否足够
            if ((inventory.getQuantity() - allotQuantity) < 0) {
                throw new RuntimeException("商品编码:【" + inventory.getGsmGoodsOid() + "】" +
                        " 商品名称:【" + inventory.getGsmGoodsName() + "】" +
                        " 批号:【" + inventory.getPh() + "】" +
                        " 批次号:【" + inventory.getPch() + "】" +
                        " 库存数量不足");
            }
            inventory.setQuantity(allotQuantity); // 修改数量为调拨数量进行扣减
        }
        // 进行库存数量扣减
        inventoryService.updateQuantityByList(inventoryList);

        // 调入操作(将库存复制到目标仓库)
        for (Inventory inventory : inventoryList) {
            int inventoryId = inventory.getId();
            // short allotQuantity = inventoryAllotMap.get(inventoryId).getQuantity();  // 获取调拨数量
            inventory.setId(null); // 重置对象ID
            inventory.setIymInventoryTypeId(inventoryAllotMap.get(inventoryId).getToIymInventoryTypeId());  // 修改对象仓库ID
            // inventory.setQuantity(allotQuantity); // 修改数量为调拨数量 (在调出操作时已经对其赋值)
        }
        // 保存调入库存到目标仓库
        inventoryService.save(inventoryList);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String[] creationDate,
                                                             Integer sysClinicId,
                                                             Byte approveState,
                                                             String gsmGoodsName) {
        return inventoryAllotMapper.selectClinicListByCriteria(creationDate, sysClinicId, approveState, gsmGoodsName);
    }

    @Override
    public List<Map<String, Object>> getClinicLshGroupListByCriteria(String[] creationDate,
                                                                     Integer sysClinicId,
                                                                     Byte approveState) {
        return inventoryAllotMapper.selectClinicLshGroupListByCriteria(creationDate, sysClinicId, approveState);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return inventoryAllotMapper.selectByLsh(lsh);
    }


}
