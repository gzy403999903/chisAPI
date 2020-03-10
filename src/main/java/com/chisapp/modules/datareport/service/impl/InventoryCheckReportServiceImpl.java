package com.chisapp.modules.datareport.service.impl;

import com.chisapp.common.utils.ExcelFileUtils;
import com.chisapp.modules.datareport.dao.InventoryCheckReportMapper;
import com.chisapp.modules.datareport.service.InventoryCheckReportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/8 21:59
 * @Version 1.0
 */
@Service
public class InventoryCheckReportServiceImpl implements InventoryCheckReportService {
    private InventoryCheckReportMapper inventoryReportMapper;
    @Autowired
    public void setInventoryReportMapper(InventoryCheckReportMapper inventoryReportMapper) {
        this.inventoryReportMapper = inventoryReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getClinicByCriteriaForCurrent(Integer sysClinicId, String checkWay, Integer gsmGoodsTypeId) {
        return inventoryReportMapper.selectClinicByCriteriaForCurrent(sysClinicId, checkWay, gsmGoodsTypeId);
    }

    @Override
    public XSSFWorkbook downloadExcelForCurrent(Integer sysClinicId, String checkWay, Integer gsmGoodsTypeId) {
        // 获取实盘盘点结果
        List<Map<String, Object>> bodyList = this.getClinicByCriteriaForCurrent(sysClinicId, checkWay, gsmGoodsTypeId);

        // 生成 excel 报表
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("gsmGoodsOid", "商品编码");
        titleMap.put("gsmGoodsName", "名称");
        titleMap.put("gsmGoodsSpecs", "规格");
        titleMap.put("goodsUnitsName", "单位(整)");
        titleMap.put("splitUnitsName", "单位(拆)");
        titleMap.put("originName", "产地");
        titleMap.put("manufacturerName", "生产厂家");
        titleMap.put("goodsClassifyName", "商品分类");
        titleMap.put("sysSecondClassifyName", "二级分类");
        titleMap.put("iymShelfPositionName", "货位");
        titleMap.put("ph", "批号");
        titleMap.put("expiryDate", "有效期至");
        titleMap.put("inventoryIntactQuantity", "账面数量(整)");
        titleMap.put("inventorySplitQuantity", "账面数量(拆)");
        titleMap.put("totalCostAmount", "含税成本总额");

        return ExcelFileUtils.createXSSFWorkbook(titleMap, bodyList);
    }

    @Override
    public void executePcdInventoryCheckChanged(String[] creationDate, Integer sysClinicId) {
        this.inventoryReportMapper.executePcdInventoryCheckChanged(creationDate, sysClinicId);
    }

    @Override
    public void dropPcdInventoryCheckChanged() {
        this.inventoryReportMapper.dropPcdInventoryCheckChanged();
    }

    @Override
    public List<Map<String, Object>> getClinicByCriteriaForChanged(Integer sysClinicId, String checkWay, Integer gsmGoodsTypeId) {
        return this.inventoryReportMapper.selectClinicByCriteriaForChanged(sysClinicId, checkWay, gsmGoodsTypeId);
    }

    @Override
    public XSSFWorkbook downloadExcelForChanged(Integer sysClinicId, String checkWay, Integer gsmGoodsTypeId, String[] creationDate) {
        // 执行动盘存储过程
        this.executePcdInventoryCheckChanged(creationDate, sysClinicId);
        // 获取动盘盘点结果
        List<Map<String, Object>> bodyList = this.getClinicByCriteriaForChanged(sysClinicId, checkWay, gsmGoodsTypeId);
        // 释放动盘存储过程资源
        this.dropPcdInventoryCheckChanged();

        // 生成 excel 报表
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("gsmGoodsOid", "商品编码");
        titleMap.put("gsmGoodsName", "名称");
        titleMap.put("gsmGoodsSpecs", "规格");
        titleMap.put("goodsUnitsName", "单位(整)");
        titleMap.put("splitUnitsName", "单位(拆)");
        titleMap.put("originName", "产地");
        titleMap.put("manufacturerName", "生产厂家");
        titleMap.put("goodsClassifyName", "商品分类");
        titleMap.put("iymShelfPositionName", "货位");
        titleMap.put("ph", "批号");
        titleMap.put("expiryDate", "有效期至");
        titleMap.put("inventoryIntactQuantity", "账面数量(整)");
        titleMap.put("inventorySplitQuantity", "账面数量(拆)");
        titleMap.put("totalCostAmount", "含税成本总额");

        titleMap.put("addQuantity", "+采购入库");
        titleMap.put("subtractQuantity", "-采购退货出库");
        titleMap.put("sellIntactQuantity", "-销售出库(整)");
        titleMap.put("returnedIntactQuantity", "+销售退货入库(整)");
        titleMap.put("sellSplitQuantity", "-销售出库(拆)");
        titleMap.put("returnedSplitQuantity", "+销售退货入库(拆)");
        titleMap.put("usedIntactQuantity", "-领用出库(整)");
        titleMap.put("usedSplitQuantity", "-领用出库(拆)");
        titleMap.put("lossIntactQuantity", "-报损出库(整)");
        titleMap.put("lossSplitQuantity", "-报损出库(拆)");

        return ExcelFileUtils.createXSSFWorkbook(titleMap, bodyList);
    }

}
