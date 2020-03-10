package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.doctorworkstation.bean.ClinicalHistory;
import com.chisapp.modules.doctorworkstation.bean.CommonDiagnose;
import com.chisapp.modules.doctorworkstation.dao.ClinicalHistoryMapper;
import com.chisapp.modules.doctorworkstation.service.ClinicalHistoryService;
import com.chisapp.modules.doctorworkstation.service.CommonDiagnoseService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/16 14:30
 * @Version 1.0
 */
@CacheConfig(cacheNames = "ClinicalHistory")
@Service
public class ClinicalHistoryServiceImpl implements ClinicalHistoryService {

    private ClinicalHistoryMapper clinicalHistoryMapper;
    @Autowired
    public void setClinicalHistoryMapper(ClinicalHistoryMapper clinicalHistoryMapper) {
        this.clinicalHistoryMapper = clinicalHistoryMapper;
    }

    private CommonDiagnoseService commonDiagnoseService;
    @Autowired
    public void setCommonDiagnoseService(CommonDiagnoseService commonDiagnoseService) {
        this.commonDiagnoseService = commonDiagnoseService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @CachePut(key = "#clinicalHistory.id")
    @Override
    public ClinicalHistory save(ClinicalHistory clinicalHistory) {
        // 检查是否有自定义诊断 如果有则进行保存
        String diagnoseJson =
                this.saveNonexistentDiagnose(clinicalHistory.getDiagnoseJson(), clinicalHistory.getDwtDiagnoseTypeId());
        clinicalHistory.setDiagnoseJson(diagnoseJson);

        // 保存病历
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        clinicalHistory.setSysDoctorId(user.getId());
        clinicalHistory.setCreationDate(new Date());
        clinicalHistory.setSysClinicId(user.getSysClinicId());
        clinicalHistory.setFinished(false);
        clinicalHistoryMapper.insert(clinicalHistory);

        return clinicalHistory;
    }

    @CachePut(key = "#clinicalHistory.id")
    @Override
    public ClinicalHistory update(ClinicalHistory clinicalHistory) {
        // 检查是否有自定义诊断 如果有则进行保存
        String diagnoseJson =
                this.saveNonexistentDiagnose(clinicalHistory.getDiagnoseJson(), clinicalHistory.getDwtDiagnoseTypeId());
        clinicalHistory.setDiagnoseJson(diagnoseJson);

        // 修改病历
        clinicalHistoryMapper.updateByPrimaryKey(clinicalHistory);
        return clinicalHistory;
    }

    @CacheEvict(key = "#id")
    @Override
    public void updateFinishedById(Boolean finished, Integer id) {
        clinicalHistoryMapper.updateFinishedById(finished, id);
    }

    /**
     * 将没有保存过的诊断进行保存后 以JSON格式返回
     * @param diagnoseJson
     * @param dwtDiagnoseTypeId
     * @return
     */
    private String saveNonexistentDiagnose(String diagnoseJson, Integer dwtDiagnoseTypeId) {
        // 将诊断JSON 解析成 List<Map>
        List<Map<String, String>> diagnoseMapList =
                JSONUtils.parseJsonToObject(diagnoseJson, new TypeReference<List<Map<String, String>>>() {});

        for (Map<String, String> map : diagnoseMapList) {
            if (map.get("id") == null) {
                throw new RuntimeException("缺少常用诊断 id 字段");
            }
            if (map.get("name") == null) {
                throw new RuntimeException("缺少常用诊断 name 字段");
            }
            if (map.get("code") == null) {
                throw new RuntimeException("缺少常用诊断 code 字段");
            }
            try {
                Integer.parseInt(map.get("id")); // 将不能把 ID 转成 Integer 类型的添加到常用诊断
            } catch (Exception e) {
                CommonDiagnose commonDiagnose = new CommonDiagnose();  // 创建一个常用诊断
                commonDiagnose.setName(map.get("name")); // 常用诊断名称
                commonDiagnose.setCode(map.get("code")); // 常用诊断助记码
                commonDiagnose.setDwtDiagnoseTypeId(dwtDiagnoseTypeId); // 常用诊断类型
                Integer id = commonDiagnoseService.save(commonDiagnose); // 进行保存 返回ID
                map.put("id", id.toString()); // 将 ID 更新到 map
            }
        }

        // 返回保存后的诊断 JSON
        return JSONUtils.parseObjectToJson(diagnoseMapList);
    }

    @Cacheable(key = "#id")
    @Override
    public ClinicalHistory getById(Integer id) {
        return clinicalHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String[] creationDate, Integer mrmMemberId, Integer dwtDiagnoseTypeId) {
        return clinicalHistoryMapper.selectByCriteria(creationDate, mrmMemberId, dwtDiagnoseTypeId);
    }

    @Override
    public Map<String, Object> getLastUnfinishedByCriteria(Integer mrmMemberId, Integer sysDoctorId) {
        return clinicalHistoryMapper.selectLastUnfinishedByCriteria(mrmMemberId, sysDoctorId);
    }

}
