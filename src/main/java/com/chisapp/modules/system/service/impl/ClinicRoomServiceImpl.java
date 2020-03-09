package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.ClinicRoom;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.dao.ClinicRoomMapper;
import com.chisapp.modules.system.service.ClinicRoomService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/22 13:55
 * @Version 1.0
 */
@CacheConfig(cacheNames = "ClinicRoom")
@Service
public class ClinicRoomServiceImpl implements ClinicRoomService {
    private ClinicRoomMapper clinicRoomMapper;
    @Autowired
    public void setClinicRoomMapper(ClinicRoomMapper clinicRoomMapper) {
        this.clinicRoomMapper = clinicRoomMapper;
    }

    @CacheEvict(key = "'clinicId' + #clinicRoom.sysClinicId")
    @Override
    public void save(ClinicRoom clinicRoom) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        clinicRoom.setSysClinicId(user.getSysClinicId());
        clinicRoomMapper.insert(clinicRoom);
    }

    @Caching(
            put = {
                    @CachePut(key = "#clinicRoom.id")
            },
            evict = {
                    @CacheEvict(key = "'clinicId' + #clinicRoom.sysClinicId")
            }
    )
    @Override
    public ClinicRoom update(ClinicRoom clinicRoom) {
        clinicRoomMapper.updateByPrimaryKey(clinicRoom);
        return clinicRoom;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#clinicRoom.id"),
                    @CacheEvict(key = "'clinicId' + #clinicRoom.sysClinicId")
            }
    )
    @Override
    public void delete(ClinicRoom clinicRoom) {
        clinicRoomMapper.deleteByPrimaryKey(clinicRoom.getId());
    }

    @Cacheable(key = "#id")
    @Override
    public ClinicRoom getById(Integer id) {
        return clinicRoomMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ClinicRoom> getClinicListByCriteria(Integer sysClinicId,String name) {
        return clinicRoomMapper.selectClinicListByCriteria(sysClinicId, name);
    }

    @Cacheable(key = "'clinicId' + #sysClinicId")
    @Override
    public List<ClinicRoom> getClinicListAll(Integer sysClinicId) {
        return clinicRoomMapper.selectByClinicId(sysClinicId);
    }
}
