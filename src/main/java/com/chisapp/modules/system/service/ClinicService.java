package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.Clinic;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/19 9:08
 * @Version 1.0
 */
public interface ClinicService {

    /**
     * 保存操作
     * @param clinic
     */
    @Transactional
    void save(Clinic clinic);

    /**
     * 修改操作
     * @param clinic
     * @return
     */
    @Transactional
    Clinic update(Clinic clinic);

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
    Clinic getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param name
     * @return
     */
    List<Clinic> getByCriteria(String name);

    /**
     * 获取所有 state = true 的对象集合
     * @return
     */
    List<Clinic> getEnabled();

    /**
     * 根据名称或助记码进行检索查询
     * @param name
     * @return
     */
    List<Clinic> getEnabledLikeByName(String name);

}
