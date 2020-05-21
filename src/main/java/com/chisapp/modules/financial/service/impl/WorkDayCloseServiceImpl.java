package com.chisapp.modules.financial.service.impl;

import com.chisapp.common.utils.AccountPeriod;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.bean.WorkDayClose;
import com.chisapp.modules.financial.bean.WorkGroupClose;
import com.chisapp.modules.financial.dao.WorkDayCloseMapper;
import com.chisapp.modules.financial.service.WorkDayCloseService;
import com.chisapp.modules.financial.service.WorkGroupCloseService;
import com.chisapp.modules.system.bean.Clinic;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.service.ClinicService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-16 17:21
 * @Version 1.0
 */
@CacheConfig(cacheNames = "WorkDayClose")
@Service
public class WorkDayCloseServiceImpl implements WorkDayCloseService {
    @Autowired
    private WorkDayCloseMapper workDayCloseMapper;
    @Autowired
    private WorkGroupCloseService workGroupCloseService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /* -------------------------------------------------------------------------------------------------------------- */
    @Override
    public void autoSaveList() {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        WorkDayCloseMapper mapper = batchSqlSession.getMapper(WorkDayCloseMapper.class);
        try {
            // 获取当前机构集合
            List<Clinic> clinicList = this.clinicService.getEnabled();

            // 为每个机构创建一个日结记录
            WorkDayClose workDayClose;
            for (Clinic clinic : clinicList) {
                workDayClose = new WorkDayClose();
                workDayClose.setLogicDate(new Date());
                workDayClose.setSysClinicId(clinic.getId());
                workDayClose.setCloseState(false);

                mapper.insert(workDayClose);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateToClose(Date userDate, User user) {
        userDate = DateUtils.parseToShort(userDate);
        // 检查逻辑日是否合法
        this.checkLogicDate(userDate, user.getSysClinicId());

        // 检查是否有为班结的班次
        List<WorkGroupClose> workGroupCloseList = workGroupCloseService.getUnClosedByLogicDateAndSysClinicId(userDate, user.getSysClinicId());
        if (!workGroupCloseList.isEmpty()) {
            throw new RuntimeException("当前逻辑日有未班结的班次");
        }

        // 日结操作
        WorkDayClose workDayClose = this.getByLogicDateAndSysClinicId(userDate, user.getSysClinicId());
        workDayClose.setCloseState(true);
        workDayClose.setOperatorId(user.getId());
        workDayClose.setOperateDate(new Date());
        this.workDayCloseMapper.updateByPrimaryKey(workDayClose);
    }

    @Override
    public Boolean checkLogicDate(Date userDate, Integer sysClinicId) {
        // 检查当前日期之前是否有未日结的逻辑日(按账期日)
        Date[] logicDate = new Date[]{AccountPeriod.getInstance().getBeginDate(), DateUtils.getPastDate(userDate, 1)};
        List<WorkDayClose> workDayCloseList = this.getUnClosedByLogicDateAndSysClinicId(logicDate, sysClinicId);
        if (!workDayCloseList.isEmpty()) {
            throw new RuntimeException("在当前逻辑日前有未日结逻辑日");
        }
        // 检查当前日期是否有逻辑日
        WorkDayClose workDayClose = this.getByLogicDateAndSysClinicId(userDate, sysClinicId);
        if (workDayClose == null) {
            throw new RuntimeException("当前逻辑日未记入账期");
        }

        // 检查当前逻辑日是否日结
        if (workDayClose.getCloseState()) {
            throw new RuntimeException("当前逻辑日已日结");
        }
        return true;
    }

    @Override
    public WorkDayClose getByLogicDateAndSysClinicId(Date logicDate, Integer sysClinicId) {
        return workDayCloseMapper.selectByLogicDateAndSysClinicId(logicDate, sysClinicId);
    }

    @Override
    public List<WorkDayClose> getUnClosedByLogicDateAndSysClinicId(Date[] logicDate, Integer sysClinicId) {
        return workDayCloseMapper.selectUnClosedByLogicDateAndSysClinicId(logicDate, sysClinicId);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] logicDate,
                                                   Integer sysClinicId,
                                                   Boolean closeState) {

        return workDayCloseMapper.selectByCriteria(logicDate, sysClinicId, closeState);
    }


}
