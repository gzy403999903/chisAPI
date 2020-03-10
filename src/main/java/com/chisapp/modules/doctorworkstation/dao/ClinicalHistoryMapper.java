package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.ClinicalHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClinicalHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClinicalHistory record);

    ClinicalHistory selectByPrimaryKey(Integer id);

    List<ClinicalHistory> selectAll();

    int updateByPrimaryKey(ClinicalHistory record);


    /* -------------------------------------------------------------------------------------------------------------- */

    void updateFinishedById(@Param("finished") Boolean finished, @Param("id") Integer id);

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("mrmMemberId") Integer mrmMemberId,
                                               @Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId);

    Map<String, Object> selectLastUnfinishedByCriteria(@Param("mrmMemberId") Integer mrmMemberId,
                                                       @Param("sysDoctorId") Integer sysDoctorId);
}
