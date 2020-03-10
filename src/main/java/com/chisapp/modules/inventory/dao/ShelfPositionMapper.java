package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.ShelfPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShelfPositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShelfPosition record);

    ShelfPosition selectByPrimaryKey(Integer id);

    List<ShelfPosition> selectAll();

    int updateByPrimaryKey(ShelfPosition record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<ShelfPosition> selectClinicListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                   @Param("name") String name);

    List<ShelfPosition> selectAllByClinicId(@Param("sysClinicId") Integer sysClinicId);

}
