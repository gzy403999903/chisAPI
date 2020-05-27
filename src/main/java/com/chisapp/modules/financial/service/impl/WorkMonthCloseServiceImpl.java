package com.chisapp.modules.financial.service.impl;

import com.chisapp.common.utils.AccountPeriod;
import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.datareport.service.SellRecordService;
import com.chisapp.modules.financial.bean.WorkDayClose;
import com.chisapp.modules.financial.bean.WorkMonthClose;
import com.chisapp.modules.financial.dao.WorkMonthCloseMapper;
import com.chisapp.modules.financial.service.WorkDayCloseService;
import com.chisapp.modules.financial.service.WorkMonthCloseService;
import com.chisapp.modules.system.bean.Clinic;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.service.ClinicService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-24 15:14
 * @Version 1.0
 */
@CacheConfig(cacheNames = "WorkMonthClose")
@Service
public class WorkMonthCloseServiceImpl implements WorkMonthCloseService {
    @Autowired
    private WorkMonthCloseMapper workMonthCloseMapper;
    @Autowired
    private WorkDayCloseService workDayCloseService;
    @Autowired
    private SellRecordService sellRecordService;
    @Autowired
    private ClinicService clinicService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /* -------------------------------------------------------------------------------------------------------------- */
    @Value("${work-month-close-day}")
    private int workMonthCloseDay;

    @Override
    public void saveList() {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        WorkMonthCloseMapper mapper = batchSqlSession.getMapper(WorkMonthCloseMapper.class);
        try {
            // 获取当前机构集合
            List<Clinic> clinicList = this.clinicService.getEnabled();

            // 为每个机构创建一个日结记录
            WorkMonthClose workMonthClose;
            for (Clinic clinic : clinicList) {
                workMonthClose = new WorkMonthClose();
                workMonthClose.setApYear(AccountPeriod.getInstance().getYear());
                workMonthClose.setApMonth(AccountPeriod.getInstance().getMonth());
                workMonthClose.setSysClinicId(clinic.getId());
                workMonthClose.setCloseState(false);

                mapper.insert(workMonthClose);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void updateToClose(Date userDate, User user) {
        // 检查账期是否可进行月结
        WorkMonthClose workMonthClose = this.checkAccountPeriod(userDate, user.getSysClinicId());
        // 获取月结数据
        WorkMonthClose data = this.getClinicWorkMonthCloseData(userDate, user.getSysClinicId());
        if (this.hasDisparity(data)) {
            throw new RuntimeException("本期成本结算存在差异");
        }

        workMonthClose.setHsQccb(data.getHsQccb());
        workMonthClose.setWsQccb(data.getWsQccb());
        workMonthClose.setHsCgcb(data.getHsCgcb());
        workMonthClose.setWsCgcb(data.getWsCgcb());
        workMonthClose.setHsThcb(data.getHsThcb());
        workMonthClose.setWsThcb(data.getWsThcb());
        workMonthClose.setHsXscb(data.getHsXscb());
        workMonthClose.setWsXscb(data.getWsXscb());
        workMonthClose.setHsLycb(data.getHsLycb());
        workMonthClose.setWsLycb(data.getWsLycb());
        workMonthClose.setHsBscb(data.getHsBscb());
        workMonthClose.setWsBscb(data.getWsBscb());
        workMonthClose.setHsQmcb(data.getHsQmcb());
        workMonthClose.setWsQmcb(data.getWsQmcb());

        workMonthClose.setOperatorId(user.getId());
        workMonthClose.setOperateDate(new Date());
        workMonthClose.setCloseState(true);

        // 更新月结记录
        workMonthCloseMapper.updateByPrimaryKey(workMonthClose);
    }

    private Boolean hasDisparity(WorkMonthClose workMonthClose) {
        BigDecimal disparity = workMonthClose.getHsQccb()
                .add(workMonthClose.getHsCgcb())
                .subtract(workMonthClose.getHsThcb())
                .subtract(workMonthClose.getHsXscb())
                .subtract(workMonthClose.getHsLycb())
                .subtract(workMonthClose.getHsBscb())
                .subtract(workMonthClose.getHsQmcb());
        return disparity.compareTo(new BigDecimal("0")) != 0;
    }

    @Override
    public WorkMonthClose checkAccountPeriod(Date userDate, Integer sysClinicId) {
        // 检查当前是否是月结日
        LocalDate localDate = userDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int userDay = localDate.getDayOfMonth();
        if (userDay != workMonthCloseDay) {
            throw new RuntimeException("当前账期月结日为 [" + workMonthCloseDay + "日]");
        }

        // 获取账期工具类
        AccountPeriod accountPeriod = AccountPeriod.getInstance();

        // 检查月结记录是否存在 并将其作为返回值
        WorkMonthClose workMonthClose = this.getClinicByYearAndMonth(
                accountPeriod.getYear(userDate), accountPeriod.getMonth(userDate), sysClinicId);
        if (workMonthClose == null) {
            throw new RuntimeException("当前账期尚未入账");
        }
        if (workMonthClose.getCloseState()) {
            throw new RuntimeException("当前账期已完成月结");
        }

        // 查询是否有其他未月结的记录
        List<WorkMonthClose> workMonthCloseList = this.getUnClosedByYearAndMonth(
                accountPeriod.getYear(userDate), accountPeriod.getMonth(userDate));
        if (!workMonthCloseList.isEmpty()) {
            throw new RuntimeException("当前账期之前有未月结账期");
        }

        // 检查是否有未处理的挂单
        List<Map<String, Object>> pendingMsgList = this.getPendingMsgByDateAndSysClinicId(
                accountPeriod.getBeginDate(userDate),accountPeriod.getEndDate(userDate), sysClinicId);
        if (!pendingMsgList.isEmpty()) {
            throw new RuntimeException(pendingMsgList.get(0).get("msg").toString());
        }

        // 检查是否日结
        List<WorkDayClose> workDayCloseList = this.workDayCloseService.getUnClosedByLogicDateAndSysClinicId(
                new Date[]{accountPeriod.getBeginDate(userDate), accountPeriod.getEndDate(userDate)}, sysClinicId);
        if (!workDayCloseList.isEmpty()) {
            throw new RuntimeException("尚有逻辑日未完成日结");
        }

        return workMonthClose;
    }

    @Override
    public WorkMonthClose getClinicByYearAndMonth(Integer year, Integer month, Integer sysClinicId) {
        return workMonthCloseMapper.selectClinicByYearAndMonth(year, month, sysClinicId);
    }

    @Override
    public List<WorkMonthClose> getUnClosedByYearAndMonth(Integer year, Integer month) {
        return workMonthCloseMapper.selectUnClosedByYearAndMonth(year, month);
    }

    @Override
    public List<Map<String, Object>> getPendingMsgByDateAndSysClinicId(Date beginDate, Date endDate, Integer sysClinicId) {
        List<Map<String, Object>> mapList = workMonthCloseMapper.selectPendingMsgByDateAndSysClinicId(beginDate, endDate, sysClinicId);
        List<SellRecord> sellRecordList = sellRecordService.getClinicDrugsPrescriptionGroupListFromCache();
        if (!sellRecordList.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "你有未处理的处方");
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public WorkMonthClose getClinicWorkMonthCloseData(Date userDate, Integer sysClinicId) {
        AccountPeriod accountPeriod = AccountPeriod.getInstance();
        WorkMonthClose workMonthClose = workMonthCloseMapper.selectClinicWorkMonthCloseData(
                accountPeriod.getPrevYear(userDate),
                accountPeriod.getPrevMonth(userDate),
                accountPeriod.getBeginDate(userDate),
                accountPeriod.getEndDate(userDate),
                sysClinicId);
        workMonthClose.setApYear(accountPeriod.getYear(userDate));
        workMonthClose.setApMonth(accountPeriod.getMonth(userDate));
        return workMonthClose;
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer apYear, Integer apMonth) {
        return workMonthCloseMapper.selectByCriteria(apYear, apMonth);
    }


}
