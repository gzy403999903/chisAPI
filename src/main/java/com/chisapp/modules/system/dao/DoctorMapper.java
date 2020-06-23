package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DoctorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Doctor record);

    Doctor selectByPrimaryKey(Integer id);

    List<Doctor> selectAll();

    int updateByPrimaryKey(Doctor record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<Map<String, Object>> selectClinicListByCriteria(@Param("sysClinicName") String sysClinicName,
                                                         @Param("name") String name);

    List<Map<String, Object>> selectClinicEnabled(@Param("sysClinicId") Integer sysClinicId);
}
