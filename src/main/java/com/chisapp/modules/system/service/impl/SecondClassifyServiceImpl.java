package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.SecondClassify;
import com.chisapp.modules.system.dao.SecondClassifyMapper;
import com.chisapp.modules.system.service.SecondClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/10 20:29
 * @Version 1.0
 */
@CacheConfig(cacheNames = "SecondClassify")
@Service
public class SecondClassifyServiceImpl implements SecondClassifyService {

    private SecondClassifyMapper secondClassifyMapper;
    @Autowired
    public void setSecondClassifyMapper(SecondClassifyMapper secondClassifyMapper) {
        this.secondClassifyMapper = secondClassifyMapper;
    }

    @CacheEvict(key = "#secondClassify.goodsClassifyId")
    @Override
    public void save(SecondClassify secondClassify) {
         secondClassifyMapper.insert(secondClassify);
    }

    @Caching(
            put = {
                    @CachePut(key = "#secondClassify.id")
            },
            evict = {
                    @CacheEvict(key = "#secondClassify.goodsClassifyId")
            }
    )
    @Override
    public SecondClassify update(SecondClassify secondClassify) {
        secondClassifyMapper.updateByPrimaryKey(secondClassify);
        return secondClassify;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#secondClassify.id"),
                    @CacheEvict(key = "#secondClassify.goodsClassifyId")
            }
    )
    @Override
    public void delete(SecondClassify secondClassify) {
        secondClassifyMapper.deleteByPrimaryKey(secondClassify.getId());
    }

    @Cacheable(key = "#id")
    @Override
    public SecondClassify getById(Integer id) {
        return secondClassifyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer goodsClassifyId, String name, Boolean state) {
        return secondClassifyMapper.selectByCriteria(goodsClassifyId, name, state);
    }

    @Cacheable(key = "#goodsClassifyId")
    @Override
    public List<Map<String, Object>> getEnabledByGoodsClassifyId(Integer goodsClassifyId) {
        return secondClassifyMapper.selectEnabledByGoodsClassifyId(goodsClassifyId);
    }
}
