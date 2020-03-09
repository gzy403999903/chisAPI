package com.chisapp.modules.goods.service;

import com.chisapp.modules.goods.bean.GoodsApply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/21 9:35
 * @Version 1.0
 */
public interface GoodsApplyService {

    /**
     * 添加操作
     * @param goodsApply
     */
    @Transactional
    void save(GoodsApply goodsApply);

    /**
     * 撤销操作
     * @param goodsApply
     * @return
     */
    @Transactional
    void cancel(GoodsApply goodsApply);

    /**
     * 定价操作
     * @param goodsApply
     * @return
     */
    @Transactional
    void pricing(GoodsApply goodsApply);

    /**
     * 定价驳回操作
     * @param goodsApply
     * @return
     */
    @Transactional
    void cancelPricing(GoodsApply goodsApply);

    /**
     * 项目驳回操作
     * @param goodsApply
     * @return
     */
    @Transactional
    void unapproved(GoodsApply goodsApply);

    /**
     * 通过操作
     * @param goodsApply
     * @return
     */
    @Transactional
    void approved(GoodsApply goodsApply);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    GoodsApply getById(Integer id);

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
