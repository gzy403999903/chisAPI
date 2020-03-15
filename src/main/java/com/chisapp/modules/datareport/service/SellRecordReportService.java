package com.chisapp.modules.datareport.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 16:44
 * @Version 1.0
 */
public interface SellRecordReportService {

    /**
     * 计费类型汇总表
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @return
     */
    List<Map<String, Object>> getBillingTypeGroupListByCriteria(String[] creationDate,
                                                                Integer sysClinicId,
                                                                String sysClinicName);







}
