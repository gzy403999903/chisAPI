package com.chisapp.modules.datareport.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/8 21:49
 * @Version 1.0
 */
public interface InventoryCheckReportService {

    /**
     * 机构库存盘点表 --- 实盘
     * @param sysClinicId
     * @param checkWay
     * @param gsmGoodsTypeId
     * @return
     */
    List<Map<String, Object>> getClinicByCriteriaForCurrent(Integer sysClinicId,
                                                            String checkWay,
                                                            Integer gsmGoodsTypeId);

    /**
     * 下载实盘盘点表
     * @param sysClinicId
     * @param checkWay
     * @param gsmGoodsTypeId
     * @return
     */
    XSSFWorkbook downloadExcelForCurrent(Integer sysClinicId,
                                         String checkWay,
                                         Integer gsmGoodsTypeId);

    /**
     * 执行动盘存储过程
     * @param creationDate
     * @param sysClinicId
     */
    void executePcdInventoryCheckChanged(String[] creationDate, Integer sysClinicId);

    /**
     * 释放动盘盘点资源
     */
    void dropPcdInventoryCheckChanged();

    /**
     * 机构库存盘点表 --- 动盘
     * @param sysClinicId
     * @param checkWay
     * @param gsmGoodsTypeId
     * @return
     */
    List<Map<String, Object>> getClinicByCriteriaForChanged(Integer sysClinicId,
                                                            String checkWay,
                                                            Integer gsmGoodsTypeId);

    /**
     * 下载动盘盘点表
     * @param sysClinicId
     * @param checkWay
     * @param gsmGoodsTypeId
     * @return
     */
    XSSFWorkbook downloadExcelForChanged(Integer sysClinicId,
                                         String checkWay,
                                         Integer gsmGoodsTypeId,
                                         String[] creationDate);





}
