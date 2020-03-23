package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.PracticeScope;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-22 19:14
 * @Version 1.0
 */
public interface PracticeScopeService {

    /**
     * 保存操作
     * @param practiceScope
     */
    @Transactional
    void save(PracticeScope practiceScope);

    /**
     * 编辑操作
     * @param practiceScope
     */
    @Transactional
    void update(PracticeScope practiceScope);

    /**
     * 删除操作
     * @param practiceScope
     */
    @Transactional
    void delete(PracticeScope practiceScope);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    PracticeScope getById(Integer id);

    /**
     * 根据条件获取对应的集合
     * @param practiceTypeId
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer practiceTypeId, String name);

    /**
     * 根据职业类型获取对应的职业范围
     * @param practiceTypeId
     * @return
     */
    List<PracticeScope> getByPracticeTypeId(Integer practiceTypeId);



}
