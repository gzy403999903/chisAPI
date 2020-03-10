package com.chisapp.modules.nurseworkstation.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/23 15:27
 * @Version 1.0
 */
public interface ChargeFeeService {

    /**
     * 处方结账操作
     * @param mrmMemberId
     * @param paymentRecordJson
     * @param sellRecordJson
     */
    @Transactional
    String saveForPrescription(Integer mrmMemberId, String paymentRecordJson, String sellRecordJson);

    /**
     * POS结账操作
     * @param mrmMemberId
     * @param paymentRecordJson
     * @param sellRecordJson
     * @return
     */
    @Transactional
    String saveForPos(Integer mrmMemberId, String paymentRecordJson, String sellRecordJson);

    /**
     * 退费操作
     * @param mrmMemberId
     * @param neglectQuantity
     * @param paymentRecordJson
     * @param sellRecordJson
     */
    @Transactional
    void saveForReturned(Integer mrmMemberId, Boolean neglectQuantity, String paymentRecordJson, String sellRecordJson);

    /**
     * 根据流水号获取对应的收费记录
     * @param lsh
     * @return
     */
    Map<String, Object> getChargeFeeRecordByLsh(String lsh);

}
