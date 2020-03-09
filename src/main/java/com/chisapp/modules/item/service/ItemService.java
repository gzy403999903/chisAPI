package com.chisapp.modules.item.service;

import com.chisapp.modules.item.bean.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/6 17:21
 * @Version 1.0
 */
public interface ItemService {

    /**
     * 保存操作
     * @param item
     */
    @Transactional
    void save(Item item);

    /**
     * 编辑操作
     * @param item
     * @return
     */
    @Transactional
    Item update(Item item);

    /**
     * 批量调价
     * 需封装 Item.id 和 Item.retailPrice 属性
     * 调用 cacheEvictById 清除缓存
     * @param itemList
     */
    @Transactional
    void updateRetailPriceByList(List<Item> itemList);

    /**
     * 删除对应缓存
     * @param item
     */
    void cacheEvictById(Item item);

    /**
     * 删除操作
     * @param item
     * @return
     */
    @Transactional
    Item delete(Item item);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    Item getById(Integer id);

    /**
     * 根据条件查询 (视图查询)
     * @param cimItemTypeId
     * @param state
     * @param itemClassifyId
     * @param ybItem
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer cimItemTypeId, // 项目分类ID
                                            Boolean state, // 启用状态
                                            Integer itemClassifyId, // 项目分类ID
                                            Boolean ybItem, // 是否医保项目
                                            String name // 项目名称或助记码
    );

    /**
     * 根据 name 或 code 获取对应的集合
     * 这是一个模糊查询
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByName(String name);

    /**
     * 根据查询条件获取对应的收费项目集合 (处方调用)
     * @param cimItemTypeId
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByNameForPrescription(Integer cimItemTypeId, String name);

    /**
     * 根据计费类型获取启用状态的集合
     * @param billingTypeId
     * @return
     */
    List<Item> getEnabledByBillingTypeId(Integer billingTypeId);

}
