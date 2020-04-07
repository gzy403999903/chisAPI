package com.chisapp.modules.purchase.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.purchase.bean.PurchasePlan;
import com.chisapp.modules.purchase.dao.PurchasePlanMapper;
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
 * @Date: 2019/8/28 14:00
 * @Version 1.0
 */
@Service
public class PurchasePlanServiceImpl implements PurchasePlanService {

    private PurchasePlanMapper purchasePlanMapper;
    @Autowired
    public void setPurchasePlanMapper(PurchasePlanMapper purchasePlanMapper) {
        this.purchasePlanMapper = purchasePlanMapper;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(List<PurchasePlan> purchasePlanList) {
        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取流水号
        String lsh = KeyUtils.getLSH(user.getId());
        // 初始化明细号
        int mxh = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        PurchasePlanMapper mapper = batchSqlSession.getMapper(PurchasePlanMapper.class);
        try {
            for (PurchasePlan plan : purchasePlanList) {
                plan.setLsh(lsh);
                plan.setMxh(String.valueOf(mxh++));
                plan.setSysClinicId(user.getSysClinicId());
                plan.setCreatorId(user.getId());
                plan.setCreationDate(new Date());
                plan.setApproveState(ApproveStateEnum.PENDING.getIndex());

                mapper.insert(plan);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void unapproved(List<PurchasePlan> planList) {
        if (!this.examineApproveState(planList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        purchasePlanMapper.updateApproveStateByPlanIdList(
                ApproveStateEnum.UNAPPROVED.getIndex(), user.getId(), new Date(), planList);
    }

    @Override
    public void purchasing(List<PurchasePlan> planList) {
        if (!this.examineApproveState(planList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待审批状态");
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        purchasePlanMapper.updateApproveStateByPlanIdList(
                ApproveStateEnum.PURCHASING.getIndex(), user.getId(), new Date(), planList);
    }

    @Override
    public void approved(List<PurchasePlan> planList) {
        if (!this.examineApproveState(planList, ApproveStateEnum.PURCHASING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需为待采购状态");
        }
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        purchasePlanMapper.updateApproveStateByPlanIdList(
                ApproveStateEnum.APPROVED.getIndex(), user.getId(), new Date(), planList);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String[] creationDate, Integer sysClinicId, Byte approveState, String gsmGoodsName) {
        return purchasePlanMapper.selectByCriteria(creationDate,null,null,approveState, sysClinicId,null, gsmGoodsName);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, Byte approveState, String sysClinicName, String gsmGoodsName) {
        return purchasePlanMapper.selectByCriteria(creationDate,null,null, approveState,null, sysClinicName, gsmGoodsName);
    }

    @Override
    public List<Map<String, Object>> getPendingGroupListByCriteria(String[] creationDate, String gsmGoodsName) {
        List<Map<String, Object>> mapList =
                purchasePlanMapper.selectByCriteria(creationDate, null, null, ApproveStateEnum.PENDING.getIndex(), null, null, gsmGoodsName);
        return this.getGroupList(mapList, new String[]{"gsmGoodsId"});
    }

    @Override
    public List<Map<String, Object>> getPurchasingGroupListByCriteria(String[] approveDate) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> mapList =
                purchasePlanMapper.selectByCriteria(null, approveDate, user.getId(), ApproveStateEnum.PURCHASING.getIndex(), null, null, null);
        return this.getGroupList(mapList, new String[]{"gsmGoodsId"});
    }

    @Override
    public List<Map<String, Object>> getClinicGroupListByPlanIdList(List<Integer> planIdList) {
        List<Map<String, Object>> mapList = purchasePlanMapper.selectViewByPlanIdList(planIdList);
        return this.getGroupList(mapList, new String[]{"gsmGoodsId","sysClinicId"});
    }

    @Override
    public List<PurchasePlan> getByPlanIdList(List<Integer> planIdList) {
        return purchasePlanMapper.selectByPlanIdList(planIdList);
    }
}
