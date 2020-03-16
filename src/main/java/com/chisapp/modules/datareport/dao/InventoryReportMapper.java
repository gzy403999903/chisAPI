package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 10:38
 * @Version 1.0
 */
public interface InventoryReportMapper {

    List<Map<String, Object>> selectExpiryDateListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                             @Param("sysClinicName") String sysClinicName,
                                                             @Param("filterDays") Integer filterDays);

    int countExpiryDateListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                      @Param("filterDays") Integer filterDays);












}
