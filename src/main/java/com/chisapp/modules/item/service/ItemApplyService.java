package com.chisapp.modules.item.service;

import com.chisapp.modules.item.bean.ItemApply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/6 17:21
 * @Version 1.0
 */
public interface ItemApplyService {

    /**
     * 添加操作
     * @param itemApply
     */
    @Transactional
    void save(ItemApply itemApply);

    /**
     * 撤销操作
     * @param itemApply
     * @return
     */
    @Transactional
    void cancel(ItemApply itemApply);

    /**
     * 定价操作
     * @param itemApply
     * @return
     */
    @Transactional
    void pricing(ItemApply itemApply);

    /**
     * 定价驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void cancelPricing(ItemApply itemApply);

    /**
     * 项目驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void unapproved(ItemApply itemApply);

    /**
     * 通过操作
     * @param itemApply
     * @return
     */
    @Transactional
    void approved(ItemApply itemApply);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    ItemApply getById(Integer id);

    /**
     * 根据条件查询 (视图查询)
     * @param creationDate
     * @param approveState
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(String[] creationDate, // 创建时间
                                            Byte approveState, // 申请状态
                                            String name); // 项目名称或助记码


}
