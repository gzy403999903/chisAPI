package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 16:45
 * @Version 1.0
 */
public interface SellRecordReportMapper {

    List<Map<String, Object>> selectBillingTypeGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                   @Param("sysClinicId") Integer sysClinicId,
                                                                   @Param("sysClinicName") String sysClinicName);





}
