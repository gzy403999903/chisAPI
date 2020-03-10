package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.PerformItemRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/28 23:22
 * @Version 1.0
 */
public interface PerformItemRecordService {

    /**
     * 保存操作
     * @param performItemRecord
     */
    @Transactional
    void save(PerformItemRecord performItemRecord);

    /**
     * 根据查询条件获取对象集合
     * @param creationDate
     * @param mrmMemberName
     * @param operatorName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate, String mrmMemberName, String operatorName);

}
