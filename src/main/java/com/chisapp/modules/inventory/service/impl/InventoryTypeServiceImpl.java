package com.chisapp.modules.inventory.service.impl;

import com.chisapp.modules.inventory.bean.InventoryType;
import com.chisapp.modules.inventory.dao.InventoryTypeMapper;
import com.chisapp.modules.inventory.service.InventoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/9/29 18:07
 * @Version 1.0
 */
@CacheConfig(cacheNames = "InventoryType")
@Service
public class InventoryTypeServiceImpl implements InventoryTypeService {

    private InventoryTypeMapper inventoryTypeMapper;
    @Autowired
    public void setInventoryTypeMapper(InventoryTypeMapper inventoryTypeMapper) {
        this.inventoryTypeMapper = inventoryTypeMapper;
    }

    @Cacheable(key = "'all'")
    @Override
    public List<InventoryType> getAll() {
        return inventoryTypeMapper.selectAll();
    }
}
