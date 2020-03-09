package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Clinic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClinicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Clinic record);

    Clinic selectByPrimaryKey(Integer id);

    List<Clinic> selectAll();

    int updateByPrimaryKey(Clinic record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Clinic> selectByCriteria(@Param("name") String name);

    List<Clinic> selectEnabled();

    List<Clinic> selectEnabledLikeByName(@Param("name") String name);

}
