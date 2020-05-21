package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.WorkGroupClose;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkGroupCloseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkGroupClose record);

    WorkGroupClose selectByPrimaryKey(Integer id);

    List<WorkGroupClose> selectAll();

    int updateByPrimaryKey(WorkGroupClose record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<WorkGroupClose> selectByLogicDateAndOperatorId(@Param("logicDate") Date logicDate,
                                                        @Param("operatorId") Integer operatorId);

    List<WorkGroupClose> selectUnClosedByLogicDateAndSysClinicId(@Param("logicDate") Date logicDate,
                                                                 @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectByCriteria(@Param("logicDate") String[] logicDate,
                                               @Param("operatorId") Integer operatorId,
                                               @Param("closeState") Boolean closeState,
                                               @Param("sysClinicName") String sysClinicName,
                                               @Param("operatorName") String operatorName);






}
