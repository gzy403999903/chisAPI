package com.chisapp.modules.financial.service;


import com.chisapp.modules.financial.bean.WorkMonthClose;
import com.chisapp.modules.system.bean.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-24 15:14
 * @Version 1.0
 */
public interface WorkMonthCloseService {

    /**
     * 创建月结记录
     */
    void saveList();

    /**
     * 月结操作
     * @param userDate
     * @param user
     */
    void updateToClose(Date userDate, User user);

    /**
     * 检查业务
     * @param userDate 必须为账期的结束日
     * @param sysClinicId
     * @return
     */
    WorkMonthClose checkAccountPeriod(Date userDate, Integer sysClinicId);

    /**
     * 获取指定机构指定账期月结记录
     * @param year
     * @param month
     * @param sysClinicId
     * @return
     */
    WorkMonthClose getClinicByYearAndMonth(Integer year, Integer month, Integer sysClinicId);

    /**
     * 获取本年度未月结的月结记录
     * @param year
     * @param month
     * @return
     */
    List<WorkMonthClose> getUnClosedByYearAndMonth(Integer year, Integer month);

    /**
     * 获取未审核(未记账的)业务信息
     * @param beginDate
     * @param endDate
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getPendingMsgByDateAndSysClinicId(Date beginDate, Date endDate, Integer sysClinicId);

    /**
     * 获取月结数据
     * @param prevYear
     * @param prevMonth
     * @param beginDate
     * @param endDate
     * @param sysClinicId
     * @return
     */
    WorkMonthClose getClinicWorkMonthCloseData(Integer prevYear,
                                               Integer prevMonth,
                                               Date beginDate,
                                               Date endDate,
                                               Integer sysClinicId);

}
