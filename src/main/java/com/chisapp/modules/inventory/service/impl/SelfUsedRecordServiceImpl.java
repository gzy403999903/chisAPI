package com.chisapp.modules.inventory.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.inventory.bean.Inventory;
import com.chisapp.modules.inventory.bean.SelfUsedRecord;
import com.chisapp.modules.inventory.dao.SelfUsedRecordMapper;
import com.chisapp.modules.inventory.service.InventoryService;
import com.chisapp.modules.inventory.service.SelfUsedRecordService;
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
 * @Date: 2020/1/3 11:16
 * @Version 1.0
 */
@Service
public class SelfUsedRecordServiceImpl implements SelfUsedRecordService {

    private SelfUsedRecordMapper selfUsedRecordMapper;
    @Autowired
    public void setSelfUsedRecordMapper(SelfUsedRecordMapper selfUsedRecordMapper) {
        this.selfUsedRecordMapper = selfUsedRecordMapper;
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
    public void saveList(List<SelfUsedRecord> selfUsedRecordList) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        String lsh = KeyUtils.getLSH(user.getId()); // 获取流水号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        SelfUsedRecordMapper mapper = batchSqlSession.getMapper(SelfUsedRecordMapper.class);
        try {
            for (SelfUsedRecord selfUsedRecord: selfUsedRecordList) {
                selfUsedRecord.setLsh(lsh);
                selfUsedRecord.setMxh(String.valueOf(mxh++));
                selfUsedRecord.setSysClinicId(user.getSysClinicId());
                selfUsedRecord.setCreatorId(user.getId());
                selfUsedRecord.setCreationDate(new Date());
                selfUsedRecord.setApproveState(ApproveStateEnum.PENDING.getIndex()); // 设置为待审批状态

                mapper.insert(selfUsedRecord);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateApproveState(Integer approverId, Date approveDate, Byte approveState, String lsh) {
        selfUsedRecordMapper.updateApproveState(approverId, approveDate, approveState, lsh);
    }

    @Override
    public void unapproved(String lsh) {
        // 获取对应的明细
        List<SelfUsedRecord> selfUsedRecordList = this.parseToObjectList(this.getByLsh(lsh));
        // 检查明细是否全部在给定状态
        Boolean b = this.examineApproveState(selfUsedRecordList, ApproveStateEnum.PENDING.getIndex());
        if (!b) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }
        // 执行更新
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        this.updateApproveState(user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(), lsh);
    }

    @Override
    public void approved(String lsh) {
        List<SelfUsedRecord> selfUsedRecordList = this.parseToObjectList(this.getByLsh(lsh));
        if (!this.examineApproveState(selfUsedRecordList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据需为待审核状态");
        }
        // 更新单据状态
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        this.updateApproveState(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(), lsh);

        // 创建要更新的库存集合[inventory.id inventory.quantity 必填]
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        for (SelfUsedRecord selfUsedRecord : selfUsedRecordList) {
            inventory = new Inventory();
            inventory.setId(selfUsedRecord.getIymInventoryId());
            inventory.setQuantity(selfUsedRecord.getQuantity());
        }
        // 更新库存数量
        inventoryService.updateQuantityByList(inventoryList);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer sysClinicId, String[] creationDate, String lsh, String sysClinicName, Byte approveState) {
        return selfUsedRecordMapper.selectByCriteria(sysClinicId, creationDate, lsh, sysClinicName, approveState);
    }

    @Override
    public List<Map<String, Object>> getClinicGroupListByCriteria(Integer sysClinicId, String[] creationDate, String lsh, Byte approveState) {
        return selfUsedRecordMapper.selectClinicGroupListByCriteria(sysClinicId, creationDate, lsh, approveState);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return selfUsedRecordMapper.selectByLsh(lsh);
    }


}
