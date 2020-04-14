package com.chisapp.modules.datareport.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 20:03
 * @Version 1.0
 */
public interface PaymentRecordReportService {

    /**
     * 收费方式汇总
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param creatorName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getPaymentRecordListByCriteria(String[] creationDate,
                                                             Integer sysClinicId,
                                                             String sysClinicName,
                                                             String creatorName,
                                                             String groupBy);

    /**
     * 储值方式汇总
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param creatorName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getDepositPaymentRecordListByCriteria(String[] creationDate,
                                                                    Integer sysClinicId,
                                                                    String sysClinicName,
                                                                    String creatorName,
                                                                    String groupBy);


}
