package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/8 22:00
 * @Version 1.0
 */
public interface InventoryCheckReportMapper {

    List<Map<String, Object>> selectClinicByCriteriaForCurrent(@Param("sysClinicId") Integer sysClinicId,
                                                               @Param("checkWay") String checkWay,
                                                               @Param("gsmGoodsTypeId") Integer gsmGoodsTypeId);

    void executePcdInventoryCheckChanged(String[] creationDate, Integer sysClinicId);

    void dropPcdInventoryCheckChanged();

    List<Map<String, Object>> selectClinicByCriteriaForChanged(@Param("sysClinicId") Integer sysClinicId,
                                                               @Param("checkWay") String checkWay,
                                                               @Param("gsmGoodsTypeId") Integer gsmGoodsTypeId);



}
