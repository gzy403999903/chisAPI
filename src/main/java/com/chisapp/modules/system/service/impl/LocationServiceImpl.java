package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.Location;
import com.chisapp.modules.system.dao.LocationMapper;
import com.chisapp.modules.system.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/20 21:05
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Location")
@Service
public class LocationServiceImpl implements LocationService {
    private LocationMapper locationMapper;
    @Autowired
    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Cacheable(key = "'provinceIdGroupList'")
    @Override
    public List<Map<String, Object>> getProvinceIdGroupList() {
        return locationMapper.selectProvinceIdGroupList();
    }

    @Override
    public List<Map<String, Object>> getCityIdGroupListByProvinceId(Integer provinceId) {
        return locationMapper.selectCityIdGroupListByProvinceId(provinceId);
    }

    @Override
    public List<Location> getByCityId(Integer cityId) {
        return locationMapper.selectByCityId(cityId);
    }

    @Override
    public Location getById(Integer id) {
        return locationMapper.selectByPrimaryKey(id);
    }

}
