package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.modules.doctorworkstation.bean.PerformItemRecord;
import com.chisapp.modules.doctorworkstation.dao.PerformItemRecordMapper;
import com.chisapp.modules.doctorworkstation.service.PerformItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/28 23:27
 * @Version 1.0
 */
@Service
public class PerformItemRecordServiceImpl implements PerformItemRecordService {

    private PerformItemRecordMapper performItemRecordMapper;
    @Autowired
    public void setPerformItemRecordMapper(PerformItemRecordMapper performItemRecordMapper) {
        this.performItemRecordMapper = performItemRecordMapper;
    }

    @Override
    public void save(PerformItemRecord performItemRecord) {
        performItemRecordMapper.insert(performItemRecord);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, String mrmMemberName, String operatorName) {
        return performItemRecordMapper.selectByCriteria(creationDate, mrmMemberName, operatorName);
    }
}
