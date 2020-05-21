package com.chisapp.modules.financial.service;

import com.chisapp.modules.financial.bean.WorkDayClose;
import com.chisapp.modules.system.bean.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-16 17:21
 * @Version 1.0
 */
public interface WorkDayCloseService {

    /**
     * 定时创建日结记录
     */
    @Transactional
    void autoSaveList();

    /**
     * 编辑日结记录
     * @param userDate
     * @param user
     */
    @Transactional
    void updateToClose(Date userDate, User user);

    /**
     * 检查逻辑日
     * @param userDate
     * @param sysClinicId
     * @return
     */
    Boolean checkLogicDate(Date userDate, Integer sysClinicId);

    /**
     * 根据逻辑日 和 机构ID获取对应的日结记录
     * @param logicDate
     * @param sysClinicId
     * @return
     */
    WorkDayClose getByLogicDateAndSysClinicId(Date logicDate, Integer sysClinicId);

    /**
     * 根据逻辑日 和 机构ID获取对应的未日结记录
     * @param logicDate
     * @param sysClinicId
     * @return
     */
    List<WorkDayClose> getUnClosedByLogicDateAndSysClinicId(Date logicDate[], Integer sysClinicId);

    /**
     * 根据条件获取对应的日结记录
     * @param logicDate
     * @param sysClinicId
     * @param closeState
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] logicDate,
                                            Integer sysClinicId,
                                            Boolean closeState);
}
