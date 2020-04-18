package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.ClinicSellTarget;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-16 14:56
 * @Version 1.0
 */
public interface ClinicSellTargetService {

    /**
     * 保存操作
     * @param clinicSellTarget
     */
    @Transactional
    void save(ClinicSellTarget clinicSellTarget);

    /**
     * 编辑操作
     * @param clinicSellTarget
     */
    @Transactional
    void update(ClinicSellTarget clinicSellTarget);

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
    ClinicSellTarget getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param sysClinicName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String sysClinicName);






}
