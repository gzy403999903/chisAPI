package com.chisapp.modules.financial.service.impl;

import com.chisapp.modules.financial.bean.WorkGroup;
import com.chisapp.modules.financial.dao.WorkGroupMapper;
import com.chisapp.modules.financial.service.WorkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-05-15 16:34
 * @Version 1.0
 */
@CacheConfig(cacheNames = "WorkGroup")
@Service
public class WorkGroupServiceImpl implements WorkGroupService {
    @Autowired
    private WorkGroupMapper workGroupMapper;
    /* -------------------------------------------------------------------------------------------------------------- */

    @CacheEvict(key = "'enabled'")
    @Override
    public void save(WorkGroup workGroup) {
        workGroupMapper.insert(workGroup);
    }

    @CacheEvict(key = "'enabled'")
    @Override
    public WorkGroup update(WorkGroup workGroup) {
        workGroupMapper.updateByPrimaryKey(workGroup);
        return workGroup;
    }

    @CacheEvict(key = "'enabled'")
    @Override
    public void delete(Integer id) {
        workGroupMapper.deleteByPrimaryKey(id);
    }

    @Override
    public WorkGroup getById(Integer id) {
        return workGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WorkGroup> getByCriteria(String name, Boolean state) {
        return workGroupMapper.selectByCriteria(name, state);
    }

    @Cacheable(key = "'enabled'")
    @Override
    public List<WorkGroup> getEnabled() {
        return workGroupMapper.selectEnabled();
    }

}
