package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Authc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthcMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authc record);

    Authc selectByPrimaryKey(Integer id);

    List<Authc> selectAll();

    int updateByPrimaryKey(Authc record);

    /* -------------------------------------------------------------------------------------------------------------- */
    void updateRoleNamesById(@Param("roleNames") String roleNames, @Param("id") Integer id);




}
