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
     * 编辑操作
     * @param itemApply
     */
    @Transactional
    void update(ItemApply itemApply);

    /**
     * 定价操作
     * @param itemApply
     * @return
     */
    @Transactional
    void pricing(ItemApply itemApply);

    /**
     * 审核定价驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void cancelPricing(ItemApply itemApply);

    /**
     * 审核项目驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void unapproved(ItemApply itemApply);

    /**
     * 审核通过操作
     * @param itemApply
     * @return
     */
    @Transactional
    void approved(ItemApply itemApply);

    /**
     * 最终定价驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void lastCancelPricing(ItemApply itemApply);

    /**
     * 最终项目驳回操作
     * @param itemApply
     * @return
     */
    @Transactional
    void lastUnapproved(ItemApply itemApply);

    /**
     * 最终通过操作
     * @param itemApply
     * @return
     */
    @Transactional
    void lastApproved(ItemApply itemApply);

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
