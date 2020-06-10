package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.Doctor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/22 16:04
 * @Version 1.0
 */
public interface DoctorService {

    /**
     * 保存操作
     * @param doctor
     */
    @Transactional
    void save(Doctor doctor);

    /**
     * 修改操作
     * @param doctor
     * @return
     */
    @Transactional
    Doctor update(Doctor doctor);

    /**
     * 删除操作
     * @param doctor
     */
    @Transactional
    void delete(Doctor doctor);

    /**
     * 根据 ID 获取对象
     * @param id
     * @return
     */
    Doctor getById(Integer id);

    /**
     * 根据查询条件获取对应的集合
     * @param sysClinicName
     * @param name
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(String sysClinicName, String name);

    /**
     * 查询对应机构启用状态的医生信息
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getClinicEnabled(Integer sysClinicId);

    /**
     * 清除 getClinicEnabled 方法缓存
     * @param sysClinicId
     */
    void getClinicEnabledCacheEvict(Integer sysClinicId);
}
