package com.chisapp.modules.datareport.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
     * @param sellerId
     * @param sellerName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sellClassifyId
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
                                            Integer sellerId,
                                            String sellerName,
                                            String pemSupplierOid,
                                            String pemSupplierName,
                                            Integer[] sellClassifyId);

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
     * @param sellerId
     * @param sellerName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sellClassifyId
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
                                                  Integer sellerId,
                                                  String sellerName,
                                                  String pemSupplierOid,
                                                  String pemSupplierName,
                                                  Integer[] sellClassifyId);

    /**
     * 计费类型汇总表
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getBillingTypeGroupListByCriteria(String[] creationDate,
                                                                Integer sysClinicId,
                                                                String sysClinicName,
                                                                String groupBy);

    /**
     * 销售流水分析
     * @param creationDate
     * @param sysClinicId
     * @param lsh
     * @param sysClinicName
     * @param goodsMarginRate 注入查询 参数携带表达式运算符
     * @param marginRate 注入查询 参数携带表达式运算符
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
                                                          String goodsMarginRate,
                                                          String marginRate,
                                                          Integer goodsDiscountRate,
                                                          Integer itemDiscountRate,
                                                          Integer discountRate,
                                                          String groupBy);

    /**
     * 日销售报表
     * @param creationDate
     * @return
     */
    List<Map<String, Object>> getSellRecordDailyByCreationDate(String[] creationDate, Integer queryMonth);

    /**
     * 导出日销售报表
     * @param creationDate
     * @return
     */
    XSSFWorkbook downloadDaySellRecordExcel(String[] creationDate, Integer queryMonth);

    /**
     * 销售排行表
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param sysSellTypeId
     * @param entityTypeId
     * @param entityOid
     * @param entityName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getSellRecordSortByCriteria(String[] creationDate,
                                                          Integer sysClinicId,
                                                          String sysClinicName,
                                                          Integer sysSellTypeId,
                                                          Integer entityTypeId,
                                                          String entityOid,
                                                          String entityName,
                                                          String groupBy);

    /**
     * 销售统计--财报
     * @param creationDate
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getSellRecordStatisticsByCriteria(String[] creationDate, Integer sysClinicId);

    /**
     * 销售提成汇总
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param sellerName
     * @return
     */
    List<Map<String, Object>> getSellRecordCommissionByCriteria(String[] creationDate,
                                                                Integer sysClinicId,
                                                                String sysClinicName,
                                                                String sellerName);

    /**
     * 导出提成汇总
     * @param creationDate
     * @param sysClinicId
     * @param sysClinicName
     * @param sellerName
     * @return
     */
    XSSFWorkbook downloadSellRecordCommissionExcel(String[] creationDate,
                                                   Integer sysClinicId,
                                                   String sysClinicName,
                                                   String sellerName);

    /**
     * 销售协定成本
     * @param creationDate
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getSellAssessCostByCriteria(String[] creationDate,
                                                          String gsmGoodsOid,
                                                          String gsmGoodsName,
                                                          String pemSupplierOid,
                                                          String pemSupplierName,
                                                          String sysClinicName,
                                                          String groupBy);

}
