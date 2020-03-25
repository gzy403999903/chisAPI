package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.SellRecordReportMapper;
import com.chisapp.modules.datareport.service.SellRecordReportService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                                                   Integer sysClinicId,
                                                   String sysClinicName,
                                                   String lsh,
                                                   String entityName,
                                                   String mrmMemberName,
                                                   String sellerName) {
        return sellRecordReportMapper.selectByCriteria(creationDate, sysClinicId, sysClinicName,
                lsh, entityName, mrmMemberName, sellerName);
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
                                                                 Integer goodsMarginRate,
                                                                 Integer marginRate,
                                                                 Integer goodsDiscountRate,
                                                                 Integer itemDiscountRate,
                                                                 Integer discountRate,
                                                                 String groupBy) {
        // 格式化折扣率
        goodsDiscountRate = goodsDiscountRate == null ? 0 : goodsDiscountRate < 10 ? goodsDiscountRate * 10 : goodsDiscountRate;
        itemDiscountRate = itemDiscountRate == null ? 0 : itemDiscountRate < 10 ? itemDiscountRate * 10 : itemDiscountRate;
        discountRate = discountRate == null ? 0 : discountRate < 10 ? discountRate * 10 : discountRate;

        return sellRecordReportMapper.selectMarginRateListByCriteria(creationDate,
                sysClinicId, lsh, sysClinicName, goodsMarginRate, marginRate, goodsDiscountRate, itemDiscountRate,
                discountRate, groupBy);
    }


}
