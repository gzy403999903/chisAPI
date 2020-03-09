package com.chisapp.modules.system.handler;

import com.chisapp.modules.system.bean.Location;
import com.chisapp.modules.system.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/20 21:10
 * @Version 1.0
 */
@RequestMapping("/location")
@RestController
public class LocationHandler {

    private LocationService locationService;
    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * 获取所有省份列表
     * @return
     */
    @GetMapping("/getProvinceIdGroupList")
    public List<Map<String, Object>> getProvinceIdGroupList() {
        return locationService.getProvinceIdGroupList();
    }

    /**
     * 根据省份 ID 获取对应的城市集合
     * @param provinceId
     * @return
     */
    @GetMapping("/getCityIdGroupListByProvinceId")
    public List<Map<String, Object>> getCityIdGroupListByProvinceId(@RequestParam Integer provinceId) {
        return locationService.getCityIdGroupListByProvinceId(provinceId);
    }

    /**
     * 根据城市 ID 获取对应的区域集合
     * @param cityId
     * @return
     */
    @GetMapping("/getByCityId")
    public List<Location> getByCityId(@RequestParam Integer cityId) {
        return locationService.getByCityId(cityId);
    }


}
