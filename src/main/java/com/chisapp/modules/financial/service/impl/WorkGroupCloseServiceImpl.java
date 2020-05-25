package com.chisapp.modules.financial.service.impl;

import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.bean.WorkGroupClose;
import com.chisapp.modules.financial.dao.WorkGroupCloseMapper;
import com.chisapp.modules.financial.service.WorkDayCloseService;
import com.chisapp.modules.financial.service.WorkGroupCloseService;
import com.chisapp.modules.system.bean.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-16 17:02
 * @Version 1.0
 */
@CacheConfig(cacheNames = "WorkGroupClose")
@Service
public class WorkGroupCloseServiceImpl implements WorkGroupCloseService {
    @Autowired
    private WorkGroupCloseMapper workGroupCloseMapper;
    @Autowired
    private WorkDayCloseService workDayCloseService;

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取当前的代理对象
     * @return
     */
    private WorkGroupCloseServiceImpl currentProxy() {
        return (WorkGroupCloseServiceImpl) AopContext.currentProxy();
    }

    @Override
    public Boolean checkRegistration(Date userDate, User user) {
        // 检查当前逻辑日是否与服务器一致 (只有在一致时才能登记班次)
        Date serverDate = DateUtils.parseToShort(new Date());
        userDate = DateUtils.parseToShort(userDate);
        if (serverDate.compareTo(userDate) != 0) {
            throw new RuntimeException("当前逻辑日与系统逻辑日不一致");
        }

        // 检查是否登记班次 如果有未班结的班次则说明已经登记返回 true 否则返回 false
        List<WorkGroupClose> workGroupCloseList = this.currentProxy().getByLogicDateAndOperatorId(userDate, user.getId());
        for (WorkGroupClose workGroupClose : workGroupCloseList) {
            if (!workGroupClose.getCloseState()) {
                return true;
            }
        }
        return false;
    }

    private Boolean checkRepeat(Date userDate, Integer flmWorkGroupId, User user) {
        List<WorkGroupClose> workGroupCloseList = this.currentProxy().getByLogicDateAndOperatorId(userDate, user.getId());
        for (WorkGroupClose workGroupClose : workGroupCloseList) {
            if (workGroupClose.getFlmWorkGroupId().intValue() == flmWorkGroupId.intValue()) {
                return true;
            }
        }

        return false;
    }

    @CacheEvict(key = "#userDate.getTime() + '.' + #user.id")
    @Override
    public void registration(Date userDate, Integer flmWorkGroupId, User user) {
        userDate = DateUtils.parseToShort(userDate);
        // 检查逻辑日是否合法
        this.workDayCloseService.checkLogicDate(userDate, user.getSysClinicId());

        // 检查是否有未班结班次(同一个逻辑日只能有一个未结班次, 不班结无法日结, 所以不同逻辑日也不可能再次创建班次)
        if (this.checkRegistration(userDate, user)) {
            throw new RuntimeException("有未班结班次, 不能执行该操作");
        }

        // 是否有重复班次
        if (this.checkRepeat(userDate, flmWorkGroupId, user)) {
            throw new RuntimeException("该班次已使用, 请选择其他班次");
        }

        // 创建一个班次
        WorkGroupClose workGroupClose = new WorkGroupClose();
        workGroupClose.setLogicDate(new Date());
        workGroupClose.setSysClinicId(user.getSysClinicId());
        workGroupClose.setFlmWorkGroupId(flmWorkGroupId);
        workGroupClose.setOperatorId(user.getId());
        workGroupClose.setBeginDate(new Date());
        workGroupClose.setCloseState(false);

        workGroupCloseMapper.insert(workGroupClose);
    }

    @CacheEvict(key = "#workGroupClose.logicDate.getTime() + '.' + #workGroupClose.operatorId")
    @Override
    public void update(WorkGroupClose workGroupClose) {
        workGroupCloseMapper.updateByPrimaryKey(workGroupClose);
    }

    @Override
    public WorkGroupClose getById(Integer id) {
        return workGroupCloseMapper.selectByPrimaryKey(id);
    }

    @Cacheable(key = "#logicDate.getTime() + '.' + #operatorId", condition = "#logicDate.getTime() % 1000 == 0")
    @Override
    public List<WorkGroupClose> getByLogicDateAndOperatorId(Date logicDate, Integer operatorId) {
        return workGroupCloseMapper.selectByLogicDateAndOperatorId(logicDate, operatorId);
    }

    @Override
    public List<WorkGroupClose> getUnClosedByLogicDateAndSysClinicId(Date logicDate, Integer sysClinicId) {
        return workGroupCloseMapper.selectUnClosedByLogicDateAndSysClinicId(logicDate, sysClinicId);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] logicDate,
                                                   Integer operatorId,
                                                   Boolean closeState,
                                                   String sysClinicName,
                                                   String operatorName) {

        return workGroupCloseMapper.selectByCriteria(logicDate, operatorId, closeState, sysClinicName, operatorName);
    }

}
