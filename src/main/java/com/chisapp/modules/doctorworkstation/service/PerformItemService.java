package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.PerformItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/26 14:24
 * @Version 1.0
 */
public interface PerformItemService {

    /**
     * 保存操作
     * @param performItemList
     */
    @Transactional
    void saveList(List<PerformItem> performItemList);

    /**
     * 根据 ID 扣减剩余次数
     * 不能直接更新 要使用原数量-对应数量
     * @param id
     * @param performQuantity
     */
    @Transactional
    void updateResidueQuantityById(Integer id, Integer performQuantity);

    /**
     * PerformItem 必须赋值 lsh, entityId, quantity
     * 将 residueQuantity 减去 quantity * once_contain_quantity
     * @param performItemList
     */
    @Transactional
    void updateResidueQuantityByList(List<PerformItem> performItemList);

    /**
     * PerformItem 必须赋值 lsh, entityId, quantity
     * 将 residueQuantity 直接清 0
     * @param performItemList
     */
    @Transactional
    void resetResidueQuantityByList(List<PerformItem> performItemList);

    /**
     * 根据条件获取对应的记录
     * @param mrmMemberId
     * @param cimItemName
     * @param showZero
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer mrmMemberId, String cimItemName, Boolean showZero);

}
