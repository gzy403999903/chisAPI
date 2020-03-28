package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.ClinicalHistoryTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/9 14:33
 * @Version 1.0
 */
public interface ClinicalHistoryTemplateService {

    /**
     * 保存操作
     * @param clinicalHistoryTemplate
     */
    @Transactional
    void save(ClinicalHistoryTemplate clinicalHistoryTemplate);

    /**
     * 编辑操作
     * @param clinicalHistoryTemplate
     */
    @Transactional
    void update(ClinicalHistoryTemplate clinicalHistoryTemplate);

    /**
     * 删除操作
     * @param clinicalHistoryTemplate
     */
    @Transactional
    void delete(ClinicalHistoryTemplate clinicalHistoryTemplate);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    ClinicalHistoryTemplate getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param sysDoctorId
     * @param dwtDiagnoseTypeId
     * @param shareState
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer sysDoctorId, Integer dwtDiagnoseTypeId, Boolean shareState, String name);
}
