package com.chisapp.modules.nurseworkstation.dao;

import com.chisapp.modules.nurseworkstation.bean.RegistrationRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RegistrationRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RegistrationRecord record);

    RegistrationRecord selectByPrimaryKey(Integer id);

    List<RegistrationRecord> selectAll();

    int updateByPrimaryKey(RegistrationRecord record);


    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectClinicListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                         @Param("creationDate") String[] creationDate,
                                                         @Param("mrmMemberName")String mrmMemberName,
                                                         @Param("cimItemName")String cimItemName,
                                                         @Param("sysDoctorName")String sysDoctorName);
}
