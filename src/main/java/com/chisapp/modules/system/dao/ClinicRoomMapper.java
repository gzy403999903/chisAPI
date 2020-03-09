package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.ClinicRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClinicRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClinicRoom record);

    ClinicRoom selectByPrimaryKey(Integer id);

    List<ClinicRoom> selectAll();

    int updateByPrimaryKey(ClinicRoom record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<ClinicRoom> selectClinicListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                @Param("name") String name);

    List<ClinicRoom> selectByClinicId(@Param("sysClinicId") Integer sysClinicId);

}
