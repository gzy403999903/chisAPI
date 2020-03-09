package com.chisapp.modules.goods.service;

import com.chisapp.modules.goods.bean.GoodsType;

import java.util.List;

/**
 * GoodsType 类型数据由后台手动添加
 * 业务仅提供查询操作且进行了缓存 后台数据更新必须清除缓存
 * @Author: Tandy
 * @Date: 2019/8/21 11:30
 * @Version 1.0
 */

public interface GoodsTypeService {
    /**
     * 获取所有对象
     * @return
     */
    List<GoodsType> getAll();
}
