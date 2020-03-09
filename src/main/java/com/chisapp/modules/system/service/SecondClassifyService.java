package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.SecondClassify;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/10 20:24
 * @Version 1.0
 */
public interface SecondClassifyService {

    /**
     * 保存操作
     * @param secondClassify
     */
    @Transactional
    void save(SecondClassify secondClassify);

    /**
     * 修改操作
     * @param secondClassify
     * @return
     */
    @Transactional
    SecondClassify update(SecondClassify secondClassify);

    /**
     * 删除操作
     * @param secondClassify
     */
    @Transactional
    void delete(SecondClassify secondClassify);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    SecondClassify getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param goodsClassifyId
     * @param name
     * @param state
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer goodsClassifyId, String name, Boolean state);

    /**
     * 获取所有对象集合
     * @return
     */
    List<Map<String, Object>> getEnabledByGoodsClassifyId(Integer goodsClassifyId);

}
