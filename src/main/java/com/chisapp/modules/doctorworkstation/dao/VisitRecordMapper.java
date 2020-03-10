package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.VisitRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VisitRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VisitRecord record);

    VisitRecord selectByPrimaryKey(Integer id);

    List<VisitRecord> selectAll();

    int updateByPrimaryKey(VisitRecord record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<Map<String, Object>> selectByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                               @Param("creatorId") Integer creatorId,
                                               @Param("appointmentDate") String[] appointmentDate,
                                               @Param("mrmMemberName") String mrmMemberName,
                                               @Param("finished") Boolean finished);

    List<VisitRecord> selectUnfinishedByDwtClinicalHistoryId(@Param("dwtClinicalHistoryId") Integer dwtClinicalHistoryId);
}
