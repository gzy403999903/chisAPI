package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.ExpendRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/23 17:34
 * @Version 1.0
 */
public interface ExpendRecordService {

    /**
     * 保存操作
     * @param expendRecord
     */
    @Transactional
    void save(ExpendRecord expendRecord);

    /**
     * 根据查询条件获取对象集合
     * @param creationDate
     * @param lsh
     * @param mrmMemberName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate, String lsh, String mrmMemberName);
}
