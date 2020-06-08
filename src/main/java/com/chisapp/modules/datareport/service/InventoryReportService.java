package com.chisapp.modules.datareport.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 10:37
 * @Version 1.0
 */
public interface InventoryReportService {

    /**
     * 近效期库存表
     * @param sysClinicId
     * @param sysClinicName
     * @param filterDays
     * @return
     */
    List<Map<String, Object>> getExpiryDateListByCriteria(Integer sysClinicId, String sysClinicName, Integer filterDays);

    /**
     * 获取近效期库存数量
     * @param sysClinicId
     * @param filterDays
     * @return
     */
    int countExpiryDateListByCriteria(Integer sysClinicId, Integer filterDays);

    /**
     * 导出近效期 Excel
     * @param sysClinicId
     * @param sysClinicName
     * @param filterDays
     * @return
     */
    XSSFWorkbook downloadExpiryDateExcel(Integer sysClinicId, String sysClinicName, Integer filterDays);

    /**
     * 库存动销分析
     * @param sysClinicId
     * @param sysClinicName
     * @param quantity 库存数量
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param days
     * @param sellFrequency
     * @return
     */
    List<Map<String, Object>> getSellFrequencyListByCriteria(Integer sysClinicId,
                                                             String sysClinicName,
                                                             Integer quantity,
                                                             Integer gsmGoodsTypeId,
                                                             String gsmGoodsOid,
                                                             String gsmGoodsName,
                                                             Integer days,
                                                             Integer sellFrequency,
                                                             Integer sellQuantity,
                                                             Integer minAge);

    /**
     * 库存协定成本
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    List<Map<String, Object>> getInventoryAssessCostByCriteria(String gsmGoodsOid,
                                                               String gsmGoodsName,
                                                               String pemSupplierOid,
                                                               String pemSupplierName,
                                                               String sysClinicName,
                                                               String groupBy);


}
