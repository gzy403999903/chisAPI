package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.Dict;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/28 19:39
 * @Version 1.0
 */
public interface DictService {

    /**
     * 保存操作
     * @param dict
     */
    @Transactional
    void save(Dict dict);

    /**
     * 修改操作
     * @param dict
     * @return
     */
    @Transactional
    Dict update(Dict dict);

    /**
     * 删除操作
     * @param dict
     */
    @Transactional
    Dict delete(Dict dict);

    /**
     * 根据 id 获取对象
     * @param id
     * @return
     */
    Dict getById(Integer id);

    /**
     * 根据条件查询
     * @param name
     * @param sysDictTypeId
     * @param state
     * @return
     */
    List<Dict> getByCriteria(String name, Integer sysDictTypeId, Boolean state);

    /**
     * 根据财务字典类型 ID 获取对应的财务字典明细集合
     * @param sysDictTypeId
     * @return
     */
    List<Dict> getEnabledByTypeId(Integer sysDictTypeId);

    List<Dict> getEnabledLikeByName(Integer sysDictTypeId, String name);
}
