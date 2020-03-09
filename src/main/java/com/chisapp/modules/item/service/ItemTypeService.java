package com.chisapp.modules.item.service;

import com.chisapp.modules.item.bean.ItemType;

import java.util.List;

/**
 * 该 service 只提供了查询功能, 且查询结果被缓存,
 * 从数据库进行添加修改操作时务必清除更新缓存
 *
 * @Author: Tandy
 * @Date: 2019/7/31 16:05
 * @Version 1.0
 */
public interface ItemTypeService {

    /**
     * 获取所有对象
     * @return
     */
    List<ItemType> getAll();
}
