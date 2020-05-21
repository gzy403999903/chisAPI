package com.chisapp.modules.inventory.service.impl;

import com.chisapp.modules.inventory.bean.ShelfPosition;
import com.chisapp.modules.inventory.dao.ShelfPositionMapper;
import com.chisapp.modules.inventory.service.ShelfPositionService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020/1/5 13:40
 * @Version 1.0
 */
@CacheConfig(cacheNames = "ShelfPosition")
@Service
public class ShelfPositionServiceImpl implements ShelfPositionService {
    private ShelfPositionMapper shelfPositionMapper;
    @Autowired
    public void setShelfPositionMapper(ShelfPositionMapper shelfPositionMapper) {
        this.shelfPositionMapper = shelfPositionMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @CacheEvict(key = "#shelfPosition.sysClinicId")
    @Override
    public void save(ShelfPosition shelfPosition) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取操作人信息

        shelfPosition.setSysClinicId(user.getSysClinicId());
        shelfPosition.setCreatorId(user.getId());
        shelfPosition.setCreationDate(new Date());
        shelfPositionMapper.insert(shelfPosition);
    }

    @CacheEvict(key = "#shelfPosition.sysClinicId")
    @Override
    public ShelfPosition update(ShelfPosition shelfPosition) {
        shelfPositionMapper.updateByPrimaryKey(shelfPosition);
        return shelfPosition;
    }

    @CacheEvict(key = "#shelfPosition.sysClinicId")
    @Override
    public void delete(ShelfPosition shelfPosition) {
        shelfPositionMapper.deleteByPrimaryKey(shelfPosition.getId());
    }

    @Override
    public ShelfPosition getById(Integer id) {
        return shelfPositionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ShelfPosition> getClinicListByCriteria(Integer sysClinicId, String name) {
        return shelfPositionMapper.selectClinicListByCriteria(sysClinicId, name);
    }

    @Cacheable(key = "#sysClinicId")
    @Override
    public List<ShelfPosition> getClinicListAll(Integer sysClinicId) {
        return shelfPositionMapper.selectAllByClinicId(sysClinicId);
    }
}
