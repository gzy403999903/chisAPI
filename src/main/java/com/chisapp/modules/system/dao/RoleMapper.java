package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Role> selectByCriteria(@Param("name") String name, @Param("state") Boolean state);

    List<Role> selectEnabled();

}
