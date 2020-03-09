package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.Location;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/20 20:50
 * @Version 1.0
 */

public interface LocationService {

    /**
     * 获取所有省份
     * @return
     */
    List<Map<String, Object>> getProvinceIdGroupList();

    /**
     * 根据省份ID 获取对应的城市集合
     * @return
     */
    List<Map<String, Object>> getCityIdGroupListByProvinceId(Integer provinceId);

    /**
     * 根据城市ID 获取对应的区域集合
     * @param cityId
     * @return
     */
    List<Location> getByCityId(Integer cityId);

    /**
     * 根据ID 获取对应的对象
     * @param id
     * @return
     */
    Location getById(Integer id);
}
