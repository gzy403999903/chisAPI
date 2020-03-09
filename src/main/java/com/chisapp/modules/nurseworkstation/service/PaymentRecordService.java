package com.chisapp.modules.nurseworkstation.service;

import com.chisapp.modules.nurseworkstation.bean.PaymentRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/23 15:08
 * @Version 1.0
 */
public interface PaymentRecordService {

    /**
     * 保存操作
     * @param paymentRecord
     */
    @Transactional
    void save(PaymentRecord paymentRecord);

    /**
     * 根据流水号获取对应的付款记录
     * @param lsh
     * @return
     */
    List<PaymentRecord> getByLsh(String lsh);

    /**
     * 根据流水号获取机构的汇总付款记录
     * @param lsh
     * @return
     */
    Map<String, Object> getClinicGroupByLsh(Integer sysClinicId, String lsh);

    /**
     * 获取机构收费明细
     * @param creationDate
     * @param sysClinicId
     * @param lsh
     * @param creatorName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String[] creationDate, Integer sysClinicId, String lsh, String creatorName);

}
