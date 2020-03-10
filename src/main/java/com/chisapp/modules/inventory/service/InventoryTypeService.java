package com.chisapp.modules.inventory.service;

import com.chisapp.modules.inventory.bean.InventoryType;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/9/29 18:04
 * @Version 1.0
 */
public interface InventoryTypeService {
    /**
     * 获取所有对象
     * @return
     */
    List<InventoryType> getAll();
}
