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
     * 销售明细
     * @param creationDate
     * @param invoiceDate
     * @param sysClinicId
     * @param sysClinicName
     * @param lsh
     * @param sysSellTypeId
     * @param entityTypeId
     * @param entityOid
     * @param entityName
     * @param mrmMemberName
     * @param phone
     * @param sellerName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate,
                                            String[] invoiceDate,
                                            Integer sysClinicId,
                                            String sysClinicName,
                                            String lsh,
                                            Integer sysSellTypeId,
                                            Integer entityTypeId,
                                            String entityOid,
                                            String entityName,
                                            String mrmMemberName,
                                            String phone,
                                            String sellerName);

    /**
     * 计算销售明细 部分数值
     * @param creationDate
     * @param invoiceDate
     * @param sysClinicId
     * @param sysClinicName
     * @param lsh
     * @param sysSellTypeId
     * @param entityTypeId
     * @param entityOid
     * @param entityName
     * @param mrmMemberName
     * @param phone
     * @param sellerName
     * @return
     */
    Map<String, Object> countSellRecordByCriteria(String[] creationDate,
                                                  String[] invoiceDate,
                                                  Integer sysClinicId,
                                                  String sysClinicName,
                                                  String lsh,
                                                  Integer sysSellTypeId,
                                                  Integer entityTypeId,
                                                  String entityOid,
                                                  String entityName,
                                                  String mrmMemberName,
                                                  String phone,
                                                  String sellerName);

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

    /**
     * 销售流水
     * @param creationDate
     * @param sysClinicId
     * @param lsh
     * @param sysClinicName
     * @param goodsMarginRate
     * @param marginRate
     * @param goodsDiscountRate
     * @param itemDiscountRate
     * @param discountRate
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getMarginRateListByCriteria(String[] creationDate,
                                                          Integer sysClinicId,
                                                          String lsh,
                                                          String sysClinicName,
                                                          Integer goodsMarginRate,
                                                          Integer marginRate,
                                                          Integer goodsDiscountRate,
                                                          Integer itemDiscountRate,
                                                          Integer discountRate,
                                                          String groupBy);





}
