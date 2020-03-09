package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /* -------------------------------------------------------------------------------------------------------------- */

    User selectByAccount(@Param("account") String account);

    List<Map<String, Object>> selectByCriteria(@Param("name") String name,
                                               @Param("sysRoleName") String sysRoleName,
                                               @Param("sysClinicName") String sysClinicName,
                                               @Param("state") Boolean state);

    List<Map<String, Object>> selectClinicEnabled(@Param("sysClinicId") Integer sysClinicId);
}
