package com.chisapp.modules.datareport.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-06-05 13:57
 * @Version 1.0
 */
public interface SupplierRebateReportService {

    /**
     * 获取库存的返利明细
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getInventoryRebateByCriteria(String gsmGoodsOid,
                                                           String gsmGoodsName,
                                                           String pemSupplierOid,
                                                           String pemSupplierName,
                                                           String sysClinicName,
                                                           String groupBy);

    /**
     * 获取销售的返利明细
     * @param creationDate
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getSellRebateByCriteria(String[] creationDate,
                                                      String gsmGoodsOid,
                                                      String gsmGoodsName,
                                                      String pemSupplierOid,
                                                      String pemSupplierName,
                                                      String sysClinicName,
                                                      String groupBy);
}
