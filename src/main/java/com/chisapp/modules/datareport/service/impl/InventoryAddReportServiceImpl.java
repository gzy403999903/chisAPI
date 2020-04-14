package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.InventoryAddReportMapper;
import com.chisapp.modules.datareport.service.InventoryAddReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-14 22:52
 * @Version 1.0
 */
@Service
public class InventoryAddReportServiceImpl implements InventoryAddReportService {

    private InventoryAddReportMapper inventoryAddReportMapper;
    @Autowired
    public void setInventoryAddReportMapper(InventoryAddReportMapper inventoryAddReportMapper) {
        this.inventoryAddReportMapper = inventoryAddReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getPurchaseCostAmountByCriteria(
            String[] creationDate, Integer sysClinicId, String sysClinicName, String pemSupplierName) {

        return inventoryAddReportMapper.selectPurchaseCostAmountByCriteria(
                creationDate, sysClinicId, sysClinicName, pemSupplierName);
    }





}
