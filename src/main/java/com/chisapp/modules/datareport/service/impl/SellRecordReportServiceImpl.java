package com.chisapp.modules.datareport.service.impl;

import com.chisapp.common.utils.ExcelFileUtils;
import com.chisapp.modules.datareport.dao.SellRecordReportMapper;
import com.chisapp.modules.datareport.service.SellRecordReportService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 16:44
 * @Version 1.0
 */
@Service
public class SellRecordReportServiceImpl implements SellRecordReportService {

    private SellRecordReportMapper sellRecordReportMapper;
    @Autowired
    public void setSellRecordReportMapper(SellRecordReportMapper sellRecordReportMapper) {
        this.sellRecordReportMapper = sellRecordReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate,
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
                                                   String sellerName) {

        return sellRecordReportMapper.selectByCriteria(
                creationDate, invoiceDate, sysClinicId, sysClinicName, lsh, sysSellTypeId, entityTypeId,
                entityOid, entityName, mrmMemberName, phone, sellerId, sellerName);
    }

    @Override
    public Map<String, Object> countSellRecordByCriteria(String[] creationDate,
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
                                                         String sellerName) {

        return sellRecordReportMapper.countSellRecordByCriteria(
                creationDate, invoiceDate, sysClinicId, sysClinicName, lsh, sysSellTypeId, entityTypeId,
                entityOid, entityName, mrmMemberName, phone, sellerId, sellerName);
    }

    @Override
    public List<Map<String, Object>> getBillingTypeGroupListByCriteria(String[] creationDate, Integer sysClinicId, String sysClinicName) {
        return sellRecordReportMapper.selectBillingTypeGroupListByCriteria(creationDate, sysClinicId, sysClinicName);
    }

    @Override
    public List<Map<String, Object>> getMarginRateListByCriteria(String[] creationDate,
                                                                 Integer sysClinicId,
                                                                 String lsh,
                                                                 String sysClinicName,
                                                                 String goodsMarginRate,
                                                                 String marginRate,
                                                                 Integer goodsDiscountRate,
                                                                 Integer itemDiscountRate,
                                                                 Integer discountRate,
                                                                 String groupBy) {
        // 格式化折扣率
        goodsDiscountRate = goodsDiscountRate == null ? null : goodsDiscountRate < 10 ? goodsDiscountRate * 10 : goodsDiscountRate;
        itemDiscountRate = itemDiscountRate == null ? null : itemDiscountRate < 10 ? itemDiscountRate * 10 : itemDiscountRate;
        discountRate = discountRate == null ? null : discountRate < 10 ? discountRate * 10 : discountRate;

        return sellRecordReportMapper.selectMarginRateListByCriteria(creationDate,
                sysClinicId, lsh, sysClinicName, goodsMarginRate, marginRate, goodsDiscountRate, itemDiscountRate,
                discountRate, groupBy);
    }

    @Override
    public List<Map<String, Object>> getDaySellRecordByCreationDate(String[] creationDate) {
        return sellRecordReportMapper.selectDaySellRecordByCreationDate(creationDate);
    }

    @Override
    public XSSFWorkbook downloadDaySellRecordExcel(String[] creationDate) {
        // 获取数据
        List<Map<String, Object>> bodyList = this.getDaySellRecordByCreationDate(creationDate);
        // 生成一列合计行
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("sysClinicName", "--- 合计 ---");
        for (Map<String, Object> map : bodyList) {
            for (String key : map.keySet()) {
                if (key.equals("sysClinicName")) {
                    continue;
                } else if (countMap.get(key) == null) {
                    countMap.put(key, map.get(key));
                } else {
                    countMap.put(key, new BigDecimal(countMap.get(key).toString()).add(new BigDecimal(map.get(key).toString())));
                }
            }
        }
        bodyList.add(countMap);

        // 生成 excel 报表
        Map<String, String> titleMap = new LinkedHashMap<>();
        titleMap.put("sysClinicName", "门诊名称");
        titleMap.put("xyf", "西药/中成药");
        titleMap.put("zyf", "中药");
        titleMap.put("wsclf", "卫生材料");
        titleMap.put("yjxmf", "医技项目");
        titleMap.put("fzxmf", "辅助项目");
        titleMap.put("qtxmf", "其他项目");
        titleMap.put("rxs", "日销售");
        titleMap.put("yxs", "月销售");

        return ExcelFileUtils.createXSSFWorkbook(titleMap, bodyList);
    }

    @Override
    public List<Map<String, Object>> getSortSellRecordByCriteria(String[] creationDate,
                                                                 Integer sysClinicId,
                                                                 String sysClinicName,
                                                                 Integer sysSellTypeId,
                                                                 Integer entityTypeId,
                                                                 String entityOid,
                                                                 String entityName) {

        return sellRecordReportMapper.selectSortSellRecordByCriteria(
                creationDate, sysClinicId, sysClinicName, sysSellTypeId, entityTypeId, entityOid, entityName);
    }


}
