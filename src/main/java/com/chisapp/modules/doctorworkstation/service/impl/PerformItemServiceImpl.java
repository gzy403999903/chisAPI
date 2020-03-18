package com.chisapp.modules.doctorworkstation.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.doctorworkstation.bean.PerformItem;
import com.chisapp.modules.doctorworkstation.bean.PerformItemRecord;
import com.chisapp.modules.doctorworkstation.dao.PerformItemMapper;
import com.chisapp.modules.doctorworkstation.service.PerformItemRecordService;
import com.chisapp.modules.doctorworkstation.service.PerformItemService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/26 14:30
 * @Version 1.0
 */
@Service
public class PerformItemServiceImpl implements PerformItemService {

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    private PerformItemMapper performItemMapper;
    @Autowired
    public void setPerformItemMapper(PerformItemMapper performItemMapper) {
        this.performItemMapper = performItemMapper;
    }

    private PerformItemRecordService performItemRecordService;
    @Autowired
    public void setPerformItemRecordService(PerformItemRecordService performItemRecordService) {
        this.performItemRecordService = performItemRecordService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveList(List<PerformItem> performItemList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        PerformItemMapper mapper = batchSqlSession.getMapper(PerformItemMapper.class);
        try {
            for (PerformItem performItem : performItemList) {
                mapper.insert(performItem);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateResidueQuantityById(Integer id, Integer performQuantity) {
        try {
            performItemMapper.updateResidueQuantityById(id, performQuantity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("剩余次数不足");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String lsh = KeyUtils.getLSH(user.getId());

        // 创建一条执行操作记录
        PerformItemRecord record = new PerformItemRecord();
        record.setLsh(lsh);
        record.setDwtPerformItemId(id);
        record.setPerformQuantity(performQuantity);
        record.setResidueQuantity(0); // 该字段暂时不使用
        record.setSysClinicId(user.getSysClinicId());
        record.setOperatorId(user.getId());
        record.setCreationDate(new Date());

        // 保存记录
        this.performItemRecordService.save(record);
    }

    @Override
    public void updateResidueQuantityByList(List<PerformItem> performItemList) {
        try {
            performItemMapper.updateResidueQuantityByList(performItemList);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("收费项目剩余次数不足");
        }
    }

    @Override
    public void resetResidueQuantityByList(List<PerformItem> performItemList) {
        performItemMapper.resetResidueQuantityByList(performItemList);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer mrmMemberId, String cimItemName, Integer sysClinicId, Boolean showZero) {
        return performItemMapper.selectByCriteria(mrmMemberId, cimItemName, sysClinicId, showZero);
    }

}
