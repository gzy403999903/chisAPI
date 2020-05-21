package com.chisapp.modules.financial.service;

import com.chisapp.modules.financial.bean.WorkGroupClose;
import com.chisapp.modules.system.bean.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-16 17:02
 * @Version 1.0
 */
public interface WorkGroupCloseService {
    /**
     * 检查是否登记班次
     * @param userDate
     * @param user
     * @return
     */
    Boolean checkRegistration(Date userDate, User user);

    /**
     * 登记班次
     * @param userDate
     * @param flmWorkGroupId
     * @param user
     */
    @Transactional
    void registration(Date userDate, Integer flmWorkGroupId, User user);

    /**
     * 编辑班次记录
     * @param workGroupClose
     */
    @Transactional
    void update(WorkGroupClose workGroupClose);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    WorkGroupClose getById(Integer id);

    /**
     * 根据逻辑日期与操作人获取对应的班结记录
     * @param logicDate
     * @param operatorId
     * @return
     */
    List<WorkGroupClose> getByLogicDateAndOperatorId(Date logicDate, Integer operatorId);

    /**
     * 根据逻辑日与机构ID获取未班结的记录
     * @param logicDate
     * @param sysClinicId
     * @return
     */
    List<WorkGroupClose> getUnClosedByLogicDateAndSysClinicId(Date logicDate, Integer sysClinicId);

    /**
     * 根据条件获取对应的班结记录集合
     * @param logicDate
     * @param operatorId
     * @param closeState
     * @param sysClinicName
     * @param operatorName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] logicDate,
                                            Integer operatorId,
                                            Boolean closeState,
                                            String sysClinicName,
                                            String operatorName);


}
