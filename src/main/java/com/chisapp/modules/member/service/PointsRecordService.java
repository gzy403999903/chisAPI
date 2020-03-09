package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.PointsRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/31 18:00
 * @Version 1.0
 */
public interface PointsRecordService {

    /**
     * 保存操作
     * @param pointsRecord
     */
    @Transactional
    void save(PointsRecord pointsRecord);

    /**
     * 根据查询条件获取对象集合
     * @param creationDate
     * @param lsh
     * @param mrmMemberName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate, String lsh, String mrmMemberName);

    /**
     * 根据流水号获取赠送积分
     * @param lsh
     * @return
     */
    Integer getSumGivenPointsByLsh(String lsh);

}
