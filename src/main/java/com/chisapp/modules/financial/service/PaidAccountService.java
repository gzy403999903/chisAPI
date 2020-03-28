package com.chisapp.modules.financial.service;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.financial.bean.PaidAccount;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/17 11:31
 * @Version 1.0
 */
public interface PaidAccountService {

    /**
     * 批量保存
     * @param paidAccountList
     */
    @Transactional
    void saveList(List<PaidAccount> paidAccountList);

    /**
     * 录入发票号
     * @param lshList
     * @param invoiceNo
     */
    @Transactional
    void updateInvoiceNo(List<String> lshList, String invoiceNo);

    /**
     * 录入凭证号
     * @param lshList
     * @param paymentNo
     */
    @Transactional
    void updatePaymentNo(List<String> lshList, String paymentNo, Integer payerId, Date payDate);

    /**
     * 驳回操作
     * @param paymentNo
     */
    @Transactional
    void unapproved(String paymentNo);

    /**
     * 通过操作
     * @param paymentNo
     */
    @Transactional
    void approved(String paymentNo);

    /**
     * 根据条件获取对应的已付汇总
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param approveState
     * @return
     */
    List<Map<String, Object>> getLshGroupListByCriteria(String[] creationDate,
                                                        Integer pemSupplierId,
                                                        Integer sysClinicId,
                                                        String invoiceNo,
                                                        String paymentNo,
                                                        Byte approveState);

    /**
     * 根据流水号获取对应的明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);

    /**
     * 根据条件获取对应的已付汇总
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param approveState
     * @param paymentNo
     * @return
     */
    List<Map<String, Object>> getPaymentNoGroupListByCriteria(String[] creationDate,
                                                              Integer pemSupplierId,
                                                              Integer sysClinicId,
                                                              Byte approveState,
                                                              String paymentNo);

    /**
     * 根据凭证号获取对应明细
     * @param paymentNo
     * @return
     */
    List<Map<String, Object>> getByPaymentNo(String paymentNo);

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 将 mapList 转成 ObjectList
     * @param mapList
     * @return
     */
    default List<PaidAccount> convertMapListToObjectList(List<Map<String, Object>> mapList) {
        String listJson = JSONUtils.parseObjectToJson(mapList);
        return JSONUtils.parseJsonToObject(listJson, new TypeReference<List<PaidAccount>>() {});
    }

    /***
     * 检查明细是否全部为给定状态
     * @param list
     * @param approveState
     * @return
     */
    default Boolean examineApproveState(List<PaidAccount> list, Byte approveState) {
        if (list.isEmpty()) {
            throw new RuntimeException("付款明细不能为空");
        }

        for (PaidAccount paidAccount : list) {
            if (paidAccount.getApproveState().byteValue() != approveState.byteValue()) {
                return false;
            }
        }
        return true;
    }

}
