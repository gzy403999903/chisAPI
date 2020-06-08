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

    List<Map<String, Object>> selectSellFrequencyListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                                @Param("sysClinicName") String sysClinicName,
                                                                @Param("quantity") Integer quantity,
                                                                @Param("gsmGoodsTypeId")  Integer gsmGoodsTypeId,
                                                                @Param("gsmGoodsOid") String gsmGoodsOid,
                                                                @Param("gsmGoodsName") String gsmGoodsName,
                                                                @Param("days") Integer days,
                                                                @Param("sellFrequency") Integer sellFrequency,
                                                                @Param("sellQuantity") Integer sellQuantity,
                                                                @Param("minAge") Integer minAge);

    List<Map<String, Object>> selectInventoryAssessCostByCriteria(@Param("gsmGoodsOid") String gsmGoodsOid,
                                                                  @Param("gsmGoodsName") String gsmGoodsName,
                                                                  @Param("pemSupplierOid") String pemSupplierOid,
                                                                  @Param("pemSupplierName") String pemSupplierName,
                                                                  @Param("sysClinicName") String sysClinicName,
                                                                  @Param("groupBy") String groupBy);









}
