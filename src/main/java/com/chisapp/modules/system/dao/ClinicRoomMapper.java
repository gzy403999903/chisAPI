package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.ClinicRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClinicRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClinicRoom record);

    ClinicRoom selectByPrimaryKey(Integer id);

    List<ClinicRoom> selectAll();

    int updateByPrimaryKey(ClinicRoom record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<Map<String, Object>> selectClinicListByCriteria(@Param("sysClinicName") String sysClinicName,
                                                         @Param("name") String name);

    List<ClinicRoom> selectByClinicId(@Param("sysClinicId") Integer sysClinicId);

}
