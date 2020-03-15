package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.SellRecordReportMapper;
import com.chisapp.modules.datareport.service.SellRecordReportService;
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
    public List<Map<String, Object>> getBillingTypeGroupListByCriteria(String[] creationDate, Integer sysClinicId, String sysClinicName) {
        return sellRecordReportMapper.selectBillingTypeGroupListByCriteria(creationDate, sysClinicId, sysClinicName);
    }











}
