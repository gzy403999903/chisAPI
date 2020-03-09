package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Location;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LocationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    Location selectByPrimaryKey(Integer id);

    List<Location> selectAll();

    int updateByPrimaryKey(Location record);

    /* ------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectProvinceIdGroupList();

    List<Map<String, Object>> selectCityIdGroupListByProvinceId(Integer provinceId);

    List<Location> selectByCityId(@Param("cityId") Integer cityId);

}
