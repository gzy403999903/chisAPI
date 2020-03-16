package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.InventoryReportMapper;
import com.chisapp.modules.datareport.service.InventoryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return inventoryReportMapper.selectExpiryDateListByCriteria(sysClinicId, sysClinicName, filterDays);
    }

    @Override
    public int countExpiryDateListByCriteria(Integer sysClinicId, Integer filterDays) {
        return inventoryReportMapper.countExpiryDateListByCriteria(sysClinicId, filterDays);
    }


}
