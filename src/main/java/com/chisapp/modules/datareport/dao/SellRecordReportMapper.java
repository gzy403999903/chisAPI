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

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("invoiceDate") String[] invoiceDate,
                                               @Param("sysClinicId") Integer sysClinicId,
                                               @Param("sysClinicName") String sysClinicName,
                                               @Param("lsh") String lsh,
                                               @Param("sysSellTypeId") Integer sysSellTypeId,
                                               @Param("entityTypeId") Integer entityTypeId,
                                               @Param("entityOid") String entityOid,
                                               @Param("entityName") String entityName,
                                               @Param("mrmMemberName") String mrmMemberName,
                                               @Param("phone") String phone,
                                               @Param("sellerId") Integer sellerId,
                                               @Param("sellerName") String sellerName);

    Map<String, Object> countSellRecordByCriteria(@Param("creationDate") String[] creationDate,
                                                  @Param("invoiceDate") String[] invoiceDate,
                                                  @Param("sysClinicId") Integer sysClinicId,
                                                  @Param("sysClinicName") String sysClinicName,
                                                  @Param("lsh") String lsh,
                                                  @Param("sysSellTypeId") Integer sysSellTypeId,
                                                  @Param("entityTypeId") Integer entityTypeId,
                                                  @Param("entityOid") String entityOid,
                                                  @Param("entityName") String entityName,
                                                  @Param("mrmMemberName") String mrmMemberName,
                                                  @Param("phone") String phone,
                                                  @Param("sellerId") Integer sellerId,
                                                  @Param("sellerName") String sellerName);

    List<Map<String, Object>> selectBillingTypeGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                   @Param("sysClinicId") Integer sysClinicId,
                                                                   @Param("sysClinicName") String sysClinicName);

    List<Map<String, Object>> selectMarginRateListByCriteria(@Param("creationDate") String[] creationDate,
                                                             @Param("sysClinicId") Integer sysClinicId,
                                                             @Param("lsh") String lsh,
                                                             @Param("sysClinicName") String sysClinicName,
                                                             @Param("goodsMarginRate") String goodsMarginRate,
                                                             @Param("marginRate") String marginRate,
                                                             @Param("goodsDiscountRate") Integer goodsDiscountRate,
                                                             @Param("itemDiscountRate") Integer itemDiscountRate,
                                                             @Param("discountRate") Integer discountRate,
                                                             @Param("groupBy") String groupBy);

    List<Map<String, Object>> selectSellRecordDailyByCreationDate(@Param("creationDate")String[] creationDate);

    List<Map<String, Object>> selectSellRecordSortByCriteria(@Param("creationDate") String[] creationDate,
                                                             @Param("sysClinicId") Integer sysClinicId,
                                                             @Param("sysClinicName") String sysClinicName,
                                                             @Param("sysSellTypeId") Integer sysSellTypeId,
                                                             @Param("entityTypeId") Integer entityTypeId,
                                                             @Param("entityOid") String entityOid,
                                                             @Param("entityName") String entityName);


    List<Map<String, Object>> selectSellRecordStatisticsByCriteria(@Param("creationDate") String[] creationDate,
                                                                   @Param("sysClinicId") Integer sysClinicId);



}
