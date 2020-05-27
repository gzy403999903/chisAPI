package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.WorkMonthClose;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkMonthCloseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkMonthClose record);

    WorkMonthClose selectByPrimaryKey(Integer id);

    List<WorkMonthClose> selectAll();

    int updateByPrimaryKey(WorkMonthClose record);

    /* -------------------------------------------------------------------------------------------------------------- */

    WorkMonthClose selectClinicByYearAndMonth(@Param("year") Integer year,
                                              @Param("month") Integer month,
                                              @Param("sysClinicId") Integer sysClinicId);

    List<WorkMonthClose> selectUnClosedByYearAndMonth(@Param("year") Integer year,
                                                      @Param("month") Integer month);

    List<Map<String, Object>> selectPendingMsgByDateAndSysClinicId(@Param("beginDate") Date beginDate,
                                                                   @Param("endDate") Date endDate,
                                                                   @Param("sysClinicId") Integer sysClinicId);

    WorkMonthClose selectClinicWorkMonthCloseData(@Param("prevYear") Integer prevYear,
                                                  @Param("prevMonth") Integer prevMonth,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate,
                                                  @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectByCriteria(@Param("apYear") Integer apYear,
                                               @Param("apMonth")Integer apMonth);





}
