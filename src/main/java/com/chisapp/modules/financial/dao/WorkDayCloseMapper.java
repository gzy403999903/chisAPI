package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.WorkDayClose;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkDayCloseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkDayClose record);

    WorkDayClose selectByPrimaryKey(Integer id);

    List<WorkDayClose> selectAll();

    int updateByPrimaryKey(WorkDayClose record);

    /* -------------------------------------------------------------------------------------------------------------- */
    WorkDayClose selectByLogicDateAndSysClinicId(@Param("logicDate") Date logicDate,
                                                 @Param("sysClinicId") Integer sysClinicId);

    List<WorkDayClose> selectUnClosedByLogicDateAndSysClinicId(@Param("logicDate") Date[] logicDate,
                                                               @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectByCriteria(@Param("logicDate") String[] logicDate,
                                               @Param("sysClinicId") Integer sysClinicId,
                                               @Param("closeState") Boolean closeState);


}
