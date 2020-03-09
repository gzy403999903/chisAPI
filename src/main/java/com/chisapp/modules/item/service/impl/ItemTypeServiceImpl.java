package com.chisapp.modules.item.service.impl;

import com.chisapp.modules.item.bean.ItemType;
import com.chisapp.modules.item.dao.ItemTypeMapper;
import com.chisapp.modules.item.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/31 16:09
 * @Version 1.0
 */
@CacheConfig(cacheNames = "ItemType")
@Service
public class ItemTypeServiceImpl implements ItemTypeService {

    private ItemTypeMapper itemTypeMapper;
    @Autowired
    public void setItemTypeMapper(ItemTypeMapper itemTypeMapper) {
        this.itemTypeMapper = itemTypeMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Cacheable(key = "'all'")
    @Override
    public List<ItemType> getAll() {
        return itemTypeMapper.selectAll();
    }
}
