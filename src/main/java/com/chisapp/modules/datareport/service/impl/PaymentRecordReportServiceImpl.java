package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.PaymentRecordReportMapper;
import com.chisapp.modules.datareport.service.PaymentRecordReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-15 20:04
 * @Version 1.0
 */
@Service
public class PaymentRecordReportServiceImpl implements PaymentRecordReportService {

    private PaymentRecordReportMapper paymentRecordReportMapper;
    @Autowired
    public void setPaymentRecordReportMapper(PaymentRecordReportMapper paymentRecordReportMapper) {
        this.paymentRecordReportMapper = paymentRecordReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getPaymentRecordListByCriteria(String[] creationDate,
                                                                    Integer sysClinicId,
                                                                    String sysClinicName,
                                                                    String creatorName) {
        return paymentRecordReportMapper.selectPaymentRecordListByCriteria(creationDate, sysClinicId, sysClinicName, creatorName);
    }

    @Override
    public List<Map<String, Object>> getDepositPaymentRecordListByCriteria(String[] creationDate,
                                                                           Integer sysClinicId,
                                                                           String sysClinicName,
                                                                           String creatorName) {
        return paymentRecordReportMapper.selectDepositPaymentRecordListByCriteria(creationDate, sysClinicId, sysClinicName, creatorName);
    }


}
