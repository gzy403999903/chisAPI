package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-14 22:52
 * @Version 1.0
 */
public interface InventoryAddReportMapper {

    List<Map<String, Object>> selectPurchaseCostAmountByCriteria(@Param("creationDate") String[] creationDate,
                                                                 @Param("sysClinicId") Integer sysClinicId,
                                                                 @Param("sysClinicName") String sysClinicName,
                                                                 @Param("pemSupplierName") String pemSupplierName);
}
