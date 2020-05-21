package com.chisapp.modules.financial.service;

import com.chisapp.modules.financial.bean.WorkGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-05-15 16:33
 * @Version 1.0
 */
public interface WorkGroupService {

    /**
     * 保存操作
     * @param workGroup
     */
    @Transactional
    void save(WorkGroup workGroup);

    /**
     * 编辑操作
     * @param workGroup
     * @return
     */
    @Transactional
    WorkGroup update(WorkGroup workGroup);

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
    WorkGroup getById(Integer id);

    /**
     * 根据条件获取对象的集合
     * @param name
     * @param state
     * @return
     */
    List<WorkGroup> getByCriteria(String name, Boolean state);

    /**
     * 获取所有启用对象的集合
     * @return
     */
    List<WorkGroup> getEnabled();

}
