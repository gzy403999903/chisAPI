package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 20:05
 * @Version 1.0
 */
public interface PaymentRecordReportMapper {

    List<Map<String, Object>> selectPaymentRecordListByCriteria(@Param("creationDate") String[] creationDate,
                                                                @Param("sysClinicId") Integer sysClinicId,
                                                                @Param("sysClinicName") String sysClinicName,
                                                                @Param("creatorName") String creatorName);

    List<Map<String, Object>> selectDepositPaymentRecordListByCriteria(@Param("creationDate") String[] creationDate,
                                                                       @Param("sysClinicId") Integer sysClinicId,
                                                                       @Param("sysClinicName") String sysClinicName,
                                                                       @Param("creatorName") String creatorName);


}
