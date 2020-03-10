package com.chisapp.modules.purchase.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.purchase.bean.PurchaseOrder;
import com.chisapp.modules.purchase.dao.PurchaseOrderMapper;
import com.chisapp.modules.purchase.service.PurchaseOrderService;
import com.chisapp.modules.purchase.service.PurchasePlanService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/9/2 17:16
 * @Version 1.0
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    public void setPurchaseOrderMapper(PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    private PurchasePlanService purchasePlanService;
    @Autowired
    public void setPurchasePlanService(PurchasePlanService purchasePlanService) {
        this.purchasePlanService = purchasePlanService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @Override
    public void save(List<PurchaseOrder> purchaseOrderList, List<Integer> planIdList) {
        // 修改采购计划为通过状态(该操作不成功则不继续往下执行)
        purchasePlanService.approved(purchasePlanService.getByPlanIdList(planIdList));

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取流水号
        String lsh = KeyUtils.getLSH(user.getId());
        // 初始化明细号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        PurchaseOrderMapper mapper = batchSqlSession.getMapper(PurchaseOrderMapper.class);
        try {
            for (PurchaseOrder order : purchaseOrderList) {
                order.setLsh(lsh);
                order.setMxh(String.valueOf(mxh++));
                order.setCreatorId(user.getId());
                order.setCreationDate(new Date());
                order.setApproveState(ApproveStateEnum.PENDING.getIndex());
                order.setInventoryState(false);

                mapper.insert(order);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }

    }

    @Override
    public void unapproved(String lsh) {
        List<PurchaseOrder> purchaseOrderList = this.getByLsh(lsh);
        if (!this.examineApproveState(purchaseOrderList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        purchaseOrderMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.UNAPPROVED.getIndex(),
                lsh, ApproveStateEnum.PENDING.getIndex());
    }

    @Override
    public void approved(String lsh) {
        List<PurchaseOrder> purchaseOrderList = this.getByLsh(lsh);
        if (!this.examineApproveState(purchaseOrderList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        purchaseOrderMapper.updateApproveStateByLsh(user.getId(), new Date(), ApproveStateEnum.APPROVED.getIndex(),
                lsh, ApproveStateEnum.PENDING.getIndex());
    }

    @Override
    public void updateInventoryStateByCriteria(Boolean inventoryState, List<PurchaseOrder> purchaseOrderList) {
        if (purchaseOrderList.size() == 0) {
            throw new RuntimeException("未能获取对应的采购计划明细");
        }
        for (PurchaseOrder PurchaseOrder : purchaseOrderList) {
            if (PurchaseOrder.getInventoryState()) {
                throw new RuntimeException("操作未被允许, 单据明细需为未入库状态");
            }
        }
        String lsh = purchaseOrderList.get(0).getLsh();
        Integer sysClinicId = purchaseOrderList.get(0).getSysClinicId();
        purchaseOrderMapper.updateInventoryStateByCriteria(inventoryState, lsh, sysClinicId);
    }

    @Override
    public List<Map<String, Object>> getLshGroupListByCriteria(String[] creationDate, Byte approveState, String lsh, String pemSupplierName) {
        return purchaseOrderMapper.selectLshGroupListByCriteria(creationDate, approveState, lsh, pemSupplierName);
    }

    @Override
    public List<Map<String, Object>> getGoodsGroupListByLsh(String lsh) {
        return purchaseOrderMapper.selectGoodsGroupListByLsh(lsh);
    }

    @Override
    public List<PurchaseOrder> getByLsh(String lsh) {
        return purchaseOrderMapper.selectByLsh(lsh);
    }

    @Override
    public List<Map<String, Object>> getClinicLshGroupListByInventoryState(Integer sysClinicId, Boolean inventoryState) {
        return purchaseOrderMapper.selectClinicLshGroupListByInventoryState(sysClinicId, inventoryState);
    }

    @Override
    public List<Map<String, Object>> getClinicListByLsh(String lsh, Integer sysClinicId) {
        return purchaseOrderMapper.selectClinicListByLsh(lsh, sysClinicId);
    }

    @Override
    public List<Map<String, Object>> getTrackListByLsh(String lsh) {
        return purchaseOrderMapper.selectTrackListByLsh(lsh);
    }


}
