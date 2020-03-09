package com.chisapp.modules.member.service.impl;

import com.chisapp.modules.member.bean.PointsRecord;
import com.chisapp.modules.member.dao.PointsRecordMapper;
import com.chisapp.modules.member.service.PointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/31 18:02
 * @Version 1.0
 */
@Service
public class PointsRecordServiceImpl implements PointsRecordService {

    private PointsRecordMapper pointsRecordMapper;
    @Autowired
    public void setPointsRecordMapper(PointsRecordMapper pointsRecordMapper) {
        this.pointsRecordMapper = pointsRecordMapper;
    }

    @Override
    public void save(PointsRecord pointsRecord) {
        pointsRecordMapper.insert(pointsRecord);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, String lsh, String mrmMemberName) {
        return pointsRecordMapper.selectByCriteria(creationDate, lsh, mrmMemberName);
    }

    @Override
    public Integer getSumGivenPointsByLsh(String lsh) {
        return pointsRecordMapper.selectSumGivenPointsByLsh(lsh);
    }
}
