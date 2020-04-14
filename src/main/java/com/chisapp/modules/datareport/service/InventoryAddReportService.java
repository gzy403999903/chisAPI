package com.chisapp.modules.datareport.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-14 22:51
 * @Version 1.0
 */
public interface InventoryAddReportService {

    /**
     * 采购成本 -- 财报
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param pemSupplierName
     * @return
     */
    List<Map<String, Object>> getPurchaseCostAmountByCriteria(String[] creationDate,
                                                              Integer sysClinicId,
                                                              String sysClinicName,
                                                              String pemSupplierName);






}
