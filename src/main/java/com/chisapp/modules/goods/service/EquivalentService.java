package com.chisapp.modules.goods.service;

import com.chisapp.modules.goods.bean.Equivalent;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-29 15:13
 * @Version 1.0
 */
public interface EquivalentService {

    /**
     * 保存操作
     * @param equivalentList
     */
    @Transactional
    void saveList(List<Equivalent> equivalentList);

    /**
     * 保存操作
     * @param equivalent
     */
    @Transactional
    void save(Equivalent equivalent);

    /**
     * 编辑操作
     * @param equivalent
     */
    @Transactional
    void update(Equivalent equivalent);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    Equivalent getById(Integer id);

    /**
     * 根据条件获取对象的集合
     * @param useGsmGoodsName
     * @param referGsmGoodsName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String useGsmGoodsName, String referGsmGoodsName);

}
