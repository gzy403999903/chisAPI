package com.chisapp.modules.datareport.service.impl;

import com.chisapp.common.utils.ExcelFileUtils;
import com.chisapp.modules.datareport.dao.InventoryReportMapper;
import com.chisapp.modules.datareport.service.InventoryReportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 10:38
 * @Version 1.0
 */
@Service
public class InventoryReportServiceImpl implements InventoryReportService {

    private InventoryReportMapper inventoryReportMapper;
    @Autowired
    public void setInventoryReportMapper(InventoryReportMapper inventoryReportMapper) {
        this.inventoryReportMapper = inventoryReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getExpiryDateListByCriteria(Integer sysClinicId, String sysClinicName, Integer filterDays) {
        filterDays = filterDays == null ? 120 : filterDays; // 默认效期查询天数 120 天
        return inventoryReportMapper.selectExpiryDateListByCriteria(sysClinicId, sysClinicName, filterDays);
    }

    @Override
    public int countExpiryDateListByCriteria(Integer sysClinicId, Integer filterDays) {
        filterDays = filterDays == null ? 120 : filterDays; // 默认效期查询天数 120 天
        return inventoryReportMapper.countExpiryDateListByCriteria(sysClinicId, filterDays);
    }

    @Override
    public XSSFWorkbook downloadExpiryDateExcel(Integer sysClinicId, String sysClinicName, Integer filterDays) {
        // 获取近效期库存
        List<Map<String, Object>> bodyList = this.getExpiryDateListByCriteria(sysClinicId, sysClinicName, filterDays);

        // 生成 excel 报表
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("days", "距离过期天数");
        titleMap.put("iymInventoryTypeName", "所在库房");
        titleMap.put("gsmGoodsTypeName", "商品类型");
        titleMap.put("gsmGoodsOid", "商品编码");
        titleMap.put("gsmGoodsName", "商品名称");
        titleMap.put("gsmGoodsSpecs", "整装规格");
        titleMap.put("goodsUnitsName", "单位");
        titleMap.put("retailPrice", "零售单价");
        titleMap.put("quantity", "库存数量");
        titleMap.put("ph", "批号");
        titleMap.put("costPrice", "批次进价");
        titleMap.put("expiryDate", "有效期至");
        titleMap.put("originName", "产地");
        titleMap.put("manufacturerName", "生产厂家");
        titleMap.put("sysClinicName", "机构名称");

        return ExcelFileUtils.createXSSFWorkbook(titleMap, bodyList);
    }

    @Override
    public List<Map<String, Object>> getSellFrequencyListByCriteria(Integer sysClinicId,
                                                                    String sysClinicName,
                                                                    Integer quantity,
                                                                    Integer gsmGoodsTypeId,
                                                                    String gsmGoodsOid,
                                                                    String gsmGoodsName,
                                                                    Integer days,
                                                                    Integer sellFrequency,
                                                                    Integer sellQuantity,
                                                                    Integer minAge) {

        quantity = quantity == null ? 1 : quantity; // 默认库存数量为大于等于 1
        days = days == null ? 30 : days; // 滞销天数默认 30

        return inventoryReportMapper.selectSellFrequencyListByCriteria(
                sysClinicId, sysClinicName, quantity, gsmGoodsTypeId, gsmGoodsOid, gsmGoodsName, days, sellFrequency, sellQuantity, minAge);
    }


}
