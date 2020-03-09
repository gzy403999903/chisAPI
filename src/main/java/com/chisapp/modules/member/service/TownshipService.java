package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.Township;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/21 8:33
 * @Version 1.0
 */
public interface TownshipService {

    /**
     * 添加操作
     * @param township
     */
    @Transactional
    void save(Township township);

    /**
     * 修改操作
     * @param township
     * @return
     */
    @Transactional
    Township update(Township township);

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
    Township getById(Integer id);

    /**
     * 根据获取最大的 typeNo
     * @param typeId
     * @param sysLocationId
     * @return
     */
    Short getMaxTypeNoByCriteria(Byte typeId, Integer sysLocationId);

    /**
     * 根据查询条件获取对象集合
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(String name);

    /**
     * 获取所有对象集合
     * !!!!!如果数据过多有加载性能的问题!!!!!
     * @return
     */
    List<Map<String, Object>> getAll();





}
