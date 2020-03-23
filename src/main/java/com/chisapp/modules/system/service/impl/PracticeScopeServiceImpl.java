package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.PracticeScope;
import com.chisapp.modules.system.dao.PracticeScopeMapper;
import com.chisapp.modules.system.service.PracticeScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-03-22 19:14
 * @Version 1.0
 */
@CacheConfig(cacheNames = "PracticeScope")
@Service
public class PracticeScopeServiceImpl implements PracticeScopeService {

    private PracticeScopeMapper practiceScopeMapper;
    @Autowired
    public void setPracticeScopeMapper(PracticeScopeMapper practiceScopeMapper) {
        this.practiceScopeMapper = practiceScopeMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    @CacheEvict(key = "#practiceScope.practiceTypeId")
    @Override
    public void save(PracticeScope practiceScope) {
        practiceScopeMapper.insert(practiceScope);
    }

    @CacheEvict(key = "#practiceScope.practiceTypeId")
    @Override
    public void update(PracticeScope practiceScope) {
        practiceScopeMapper.updateByPrimaryKey(practiceScope);
    }

    @CacheEvict(key = "#practiceScope.practiceTypeId")
    @Override
    public void delete(PracticeScope practiceScope) {
        practiceScopeMapper.deleteByPrimaryKey(practiceScope.getId());
    }

    @Override
    public PracticeScope getById(Integer id) {
        return practiceScopeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer practiceTypeId, String name) {
        return practiceScopeMapper.selectByCriteria(practiceTypeId, name);
    }

    // 如果 PracticeScope.id 已经做缓存 key, 此处必须加字符串防止 practiceTypeId 与 PracticeScope.id 相同
    @Cacheable(key = "#practiceTypeId")
    @Override
    public List<PracticeScope> getByPracticeTypeId(Integer practiceTypeId) {
        return practiceScopeMapper.selectByPracticeTypeId(practiceTypeId);
    }
}
