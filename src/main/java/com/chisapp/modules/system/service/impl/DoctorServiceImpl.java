package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.Doctor;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.dao.DoctorMapper;
import com.chisapp.modules.system.service.DoctorService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/22 16:10
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Doctor")
@Service
public class DoctorServiceImpl implements DoctorService {
    private DoctorMapper doctorMapper;
    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @CacheEvict(allEntries = true)
    @Override
    public void save(Doctor doctor) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        doctor.setCreatorId(user.getId());
        doctor.setCreationDate(new Date());
        doctorMapper.insert(doctor);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Doctor update(Doctor doctor) {
        doctorMapper.updateByPrimaryKey(doctor);
        return doctor;
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(Doctor doctor) {
        doctorMapper.deleteByPrimaryKey(doctor.getId());
    }

    @Override
    public Doctor getById(Integer id) {
        return doctorMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(String sysClinicName, String name) {
        return doctorMapper.selectClinicListByCriteria(sysClinicName, name);
    }

    // 如果 Doctor.id 已经做为缓存 key, 则此处需加字符串防止 sysClinicId 与 Doctor.id 相同
    @Cacheable(key = "#sysClinicId")
    @Override
    public List<Map<String, Object>> getClinicEnabled(Integer sysClinicId) {
        return doctorMapper.selectClinicEnabled(sysClinicId);
    }

}
