package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.SupplierRebateReportMapper;
import com.chisapp.modules.datareport.service.SupplierRebateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-06-05 13:57
 * @Version 1.0
 */
@Service
public class SupplierRebateReportServiceImpl implements SupplierRebateReportService {
    @Autowired
    private SupplierRebateReportMapper supplierRebateReportMapper;

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getInventoryRebateByCriteria(String gsmGoodsOid,
                                                                  String gsmGoodsName,
                                                                  String pemSupplierOid,
                                                                  String pemSupplierName,
                                                                  String sysClinicName,
                                                                  String groupBy) {

        return supplierRebateReportMapper.selectInventoryRebateByCriteria(
                gsmGoodsOid, gsmGoodsName, pemSupplierOid, pemSupplierName, sysClinicName, groupBy);
    }

    @Override
    public List<Map<String, Object>> getSellRebateByCriteria(String[] creationDate,
                                                             String gsmGoodsOid,
                                                             String gsmGoodsName,
                                                             String pemSupplierOid,
                                                             String pemSupplierName,
                                                             String sysClinicName,
                                                             String groupBy) {

        return supplierRebateReportMapper.selectSellRebateByCriteria(
                creationDate, gsmGoodsOid, gsmGoodsName, pemSupplierOid, pemSupplierName, sysClinicName, groupBy);
    }
}
