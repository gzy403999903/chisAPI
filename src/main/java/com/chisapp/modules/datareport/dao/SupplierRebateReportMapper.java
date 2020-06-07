package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-06-05 13:58
 * @Version 1.0
 */
public interface SupplierRebateReportMapper {

    List<Map<String, Object>> selectInventoryRebateByCriteria(@Param("gsmGoodsOid") String gsmGoodsOid,
                                                              @Param("gsmGoodsName") String gsmGoodsName,
                                                              @Param("pemSupplierOid") String pemSupplierOid,
                                                              @Param("pemSupplierName") String pemSupplierName,
                                                              @Param("sysClinicName") String sysClinicName,
                                                              @Param("groupBy") String groupBy);

    List<Map<String, Object>> selectSellRebateByCriteria(@Param("creationDate") String[] creationDate,
                                                         @Param("gsmGoodsOid") String gsmGoodsOid,
                                                         @Param("gsmGoodsName") String gsmGoodsName,
                                                         @Param("pemSupplierOid") String pemSupplierOid,
                                                         @Param("pemSupplierName") String pemSupplierName,
                                                         @Param("sysClinicName") String sysClinicName,
                                                         @Param("groupBy") String groupBy);


}
