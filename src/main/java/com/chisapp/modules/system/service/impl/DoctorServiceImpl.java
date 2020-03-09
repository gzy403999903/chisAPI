package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.Doctor;
import com.chisapp.modules.system.dao.DoctorMapper;
import com.chisapp.modules.system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

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

    @Caching(
            evict = {
                    @CacheEvict(value = "Doctor:clinicEnabled", allEntries = true),
                    @CacheEvict(key = "'allEnabled'")
            }
    )
    @Override
    public void save(Doctor doctor) {
        doctorMapper.insert(doctor);
    }

    @Caching(
            put = {
                    @CachePut(key = "#doctor.id")
            },
            evict = {
                    @CacheEvict(value = "Doctor:clinicEnabled", allEntries = true),
                    @CacheEvict(key = "'allEnabled'")
            }
    )
    @Override
    public Doctor update(Doctor doctor) {
        doctorMapper.updateByPrimaryKey(doctor);
        return doctor;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#doctor.id"),
                    @CacheEvict(value = "Doctor:clinicEnabled", allEntries = true),
                    @CacheEvict(key = "'allEnabled'")
            }
    )
    @Override
    public void delete(Doctor doctor) {
        doctorMapper.deleteByPrimaryKey(doctor.getId());
    }

    @Cacheable(key = "#id")
    @Override
    public Doctor getById(Integer id) {
        return doctorMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getClinicListByCriteria(Integer sysClinicId, String name) {
        return doctorMapper.selectClinicListByCriteria(sysClinicId, name);
    }

    @Cacheable(cacheNames = "Doctor:clinicEnabled",key = "#sysClinicId")
    @Override
    public List<Map<String, Object>> getClinicEnabled(Integer sysClinicId) {
        return doctorMapper.selectClinicEnabled(sysClinicId);
    }

    @Cacheable(key = "'allEnabled'")
    @Override
    public List<Map<String, Object>> getAllEnabled() {
        return doctorMapper.selectAllEnabled();
    }


}
