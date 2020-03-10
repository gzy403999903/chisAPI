package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.modules.doctorworkstation.bean.ClinicalHistoryTemplate;
import com.chisapp.modules.doctorworkstation.dao.ClinicalHistoryTemplateMapper;
import com.chisapp.modules.doctorworkstation.service.ClinicalHistoryTemplateService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/9 14:47
 * @Version 1.0
 */
@Service
public class ClinicalHistoryTemplateServiceImpl implements ClinicalHistoryTemplateService {
    private ClinicalHistoryTemplateMapper clinicalHistoryTemplateMapper;
    @Autowired
    public void setClinicalHistoryTemplateMapper(ClinicalHistoryTemplateMapper clinicalHistoryTemplateMapper) {
        this.clinicalHistoryTemplateMapper = clinicalHistoryTemplateMapper;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(ClinicalHistoryTemplate clinicalHistoryTemplate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息

        clinicalHistoryTemplate.setSysDoctorId(user.getId());
        clinicalHistoryTemplate.setCreationDate(new Date());
        clinicalHistoryTemplate.setSysClinicId(user.getSysClinicId());
        clinicalHistoryTemplate.setShareState(false);
        clinicalHistoryTemplateMapper.insert(clinicalHistoryTemplate);
    }

    @Override
    public void update(ClinicalHistoryTemplate clinicalHistoryTemplate) {
        clinicalHistoryTemplateMapper.updateByPrimaryKey(clinicalHistoryTemplate);
    }

    @Override
    public void delete(Integer id) {
        clinicalHistoryTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ClinicalHistoryTemplate getById(Integer id) {
        return clinicalHistoryTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer sysDoctorId, Integer dwtDiagnoseTypeId, Boolean shareState, String name) {
        return clinicalHistoryTemplateMapper.selectByCriteria(sysDoctorId, dwtDiagnoseTypeId, shareState, name);
    }

}
