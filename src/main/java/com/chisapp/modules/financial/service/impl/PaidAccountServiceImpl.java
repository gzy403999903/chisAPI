package com.chisapp.modules.financial.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.financial.bean.PaidAccount;
import com.chisapp.modules.financial.dao.PaidAccountMapper;
import com.chisapp.modules.financial.service.PaidAccountService;
import com.chisapp.modules.purchase.service.SupplierService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/17 11:36
 * @Version 1.0
 */
@Service
public class PaidAccountServiceImpl implements PaidAccountService {
    private PaidAccountMapper paidAccountMapper;
    @Autowired
    public void setPaidAccountMapper(PaidAccountMapper paidAccountMapper) {
        this.paidAccountMapper = paidAccountMapper;
    }

    private SupplierService supplierService;
    @Autowired
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @Override
    public void saveList(List<PaidAccount> paidAccountList) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 获取流水号
        String lsh = KeyUtils.getLSH(user.getId());
        // 初始化明细号
        int i = 1;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        PaidAccountMapper mapper = batchSqlSession.getMapper(PaidAccountMapper.class);
        try {
            for (PaidAccount paidAccount : paidAccountList) {
                paidAccount.setLsh(lsh);
                paidAccount.setMxh(String.valueOf(i++));
                paidAccount.setSysClinicId(user.getSysClinicId());
                paidAccount.setCreatorId(user.getId());
                paidAccount.setCreationDate(new Date());
                paidAccount.setApproveState(ApproveStateEnum.PENDING.getIndex());

                mapper.insert(paidAccount);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateInvoiceNo(List<String> lshList, String invoiceNo) {
        paidAccountMapper.updateInvoiceNo(lshList, invoiceNo);
    }

    @Override
    public void updatePaymentNo(List<String> lshList, String paymentNo, Integer payerId, Date payDate) {
        paidAccountMapper.updatePaymentNo(lshList, paymentNo, payerId, payDate);
    }

    @Override
    public void unapproved(String paymentNo) {
        // 根据 付款凭证号 获取集合
        List<PaidAccount> paidAccountList = this.convertMapListToObjectList(this.getByPaymentNo(paymentNo));
        // 判断所有数据是否符合给定状态
        if (!this.examineApproveState(paidAccountList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需全部为待审批状态");
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 进行驳回操作
        paidAccountMapper.updateApproveStateByPaymentNo(paymentNo, user.getId(),  new Date(), ApproveStateEnum.UNAPPROVED.getIndex());
    }

    @Override
    public void approved(String paymentNo) {
        // 根据 付款凭证号 获取集合
        List<PaidAccount> paidAccountList = this.convertMapListToObjectList(this.getByPaymentNo(paymentNo));
        // 判断所有数据是否符合给定状态
        if (!this.examineApproveState(paidAccountList, ApproveStateEnum.PENDING.getIndex())) {
            throw new RuntimeException("操作未被允许, 单据明细需全部为待审批状态");
        }
        // 判断供应商是否同一个 [除非跳过前端验证, 否则该验证不会触发]
        Integer pemSupplierId = paidAccountList.get(0).getPemSupplierId();
        for (PaidAccount paidAccount : paidAccountList) {
            if (paidAccount.getPemSupplierId() == null) {
                throw new RuntimeException("供应商不能为空");
            }
            if (pemSupplierId.intValue() != paidAccount.getPemSupplierId().intValue()) {
                throw new RuntimeException("供应商必须一致");
            }
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 进行通过操作
        paidAccountMapper.updateApproveStateByPaymentNo(paymentNo, user.getId(),  new Date(), ApproveStateEnum.APPROVED.getIndex());

        // 扣减供应商欠款
        this.subtractArrearagesAmount(paidAccountList);
    }

    private void subtractArrearagesAmount(List<PaidAccount> paidAccountList) {
        // 获取供应商 和 已付金额
        Integer pemSupplierId = null;
        BigDecimal amount = new BigDecimal("0");
        for (PaidAccount paidAccount : paidAccountList) {
            if (pemSupplierId == null) {
                pemSupplierId = paidAccount.getPemSupplierId();
            }
            BigDecimal quantity = new BigDecimal(paidAccount.getQuantity());
            BigDecimal costPrice = paidAccount.getCostPrice();
            amount = amount.add(costPrice.multiply(quantity));
        }

        // 扣减供应商应欠款
        this.supplierService.subtractArrearagesAmount(pemSupplierId, amount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }


    @Override
    public List<Map<String, Object>> getLshGroupListByCriteria(String[] creationDate,
                                                               Integer pemSupplierId,
                                                               Integer sysClinicId,
                                                               String invoiceNo,
                                                               String paymentNo,
                                                               Byte approveState) {
        return paidAccountMapper.selectLshGroupListByCriteria(
                creationDate, pemSupplierId, sysClinicId, invoiceNo, paymentNo, approveState);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return paidAccountMapper.selectByLsh(lsh);
    }

    @Override
    public List<Map<String, Object>> getPaymentNoGroupListByCriteria(String[] creationDate, Integer pemSupplierId, Integer sysClinicId, Byte approveState, String paymentNo) {
        return paidAccountMapper.selectPaymentNoGroupListByCriteria(creationDate, pemSupplierId, sysClinicId, approveState, paymentNo);
    }

    @Override
    public List<Map<String, Object>> getByPaymentNo(String paymentNo) {
        return paidAccountMapper.selectByPaymentNo(paymentNo);
    }
}
