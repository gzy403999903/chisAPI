package com.chisapp.modules.nurseworkstation.service.impl;

import com.chisapp.modules.nurseworkstation.bean.PaymentRecord;
import com.chisapp.modules.nurseworkstation.dao.PaymentRecordMapper;
import com.chisapp.modules.nurseworkstation.service.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/23 15:11
 * @Version 1.0
 */
@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private PaymentRecordMapper paymentRecordMapper;
    @Autowired
    public void setPaymentRecordMapper(PaymentRecordMapper paymentRecordMapper) {
        this.paymentRecordMapper = paymentRecordMapper;
    }

    @Override
    public void save(PaymentRecord paymentRecord) {
        paymentRecordMapper.insert(paymentRecord);
    }

    @Override
    public List<PaymentRecord> getByLsh(String lsh) {
        return paymentRecordMapper.selectByLsh(lsh);
    }

    @Override
    public Map<String, Object> getClinicGroupByLsh(Integer sysClinicId, String lsh) {
        return this.paymentRecordMapper.selectClinicGroupByLsh(sysClinicId, lsh);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String[] creationDate, Integer sysClinicId, String lsh, String creatorName) {
        return paymentRecordMapper.selectClinicListByCriteria(creationDate, sysClinicId, lsh, creatorName);
    }

}
