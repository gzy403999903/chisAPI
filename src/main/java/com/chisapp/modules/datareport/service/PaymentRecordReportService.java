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
     * 收费记录汇总表
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param creatorName
     * @return
     */
    List<Map<String, Object>> getCreatorGroupListByCriteria(String[] creationDate,
                                                            Integer sysClinicId,
                                                            String sysClinicName,
                                                            String creatorName);




}
