package com.chisapp.modules.member.service.impl;

import com.chisapp.modules.member.bean.Township;
import com.chisapp.modules.member.dao.TownshipMapper;
import com.chisapp.modules.member.service.TownshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/21 8:38
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Township")
@Service
public class TownshipServiceImpl implements TownshipService {

    private TownshipMapper townshipMapper;
    @Autowired
    public void setTownshipMapper(TownshipMapper townshipMapper) {
        this.townshipMapper = townshipMapper;
    }

    @CacheEvict(key = "'all'")
    @Override
    public void save(Township township) {
        township.setTypeNo(this.getTypeNo(township.getTypeId(), township.getSysLocationId()));
        townshipMapper.insert(township);
    }

    @Caching(
            put = {
                    @CachePut(key = "#township.id")
            },
            evict = {
                    @CacheEvict(key = "'all'")
            }
    )
    @Override
    public Township update(Township township) {
        townshipMapper.updateByPrimaryKey(township);
        return township;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'all'")
            }
    )
    @Override
    public void delete(Integer id) {
        townshipMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(key = "#id")
    @Override
    public Township getById(Integer id) {
        return townshipMapper.selectByPrimaryKey(id);
    }

    @Override
    public Short getMaxTypeNoByCriteria(Byte typeId, Integer sysLocationId) {
        return townshipMapper.selectMaxTypeNoByCriteria(typeId, sysLocationId);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String name) {
        return townshipMapper.selectByCriteria(name);
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Map<String, Object>> getAll() {
        return townshipMapper.selectViewAll();
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取区划类型编码
     * @param typeId
     * @param sysLocationId
     * @return
     */
    private Short getTypeNo(byte typeId, int sysLocationId) {
        // 获取对应区划类型的最大编码
        short typeNo = this.getMaxTypeNoByCriteria(typeId, sysLocationId);

        // 如果没有记录则按区划类型编制起始编码
        if (typeNo == 0) {
            switch (typeId) {
                case 0: typeNo = 1;break;
                case 1: typeNo = 100;break;
                case 2: typeNo = 200;break;
                default: throw new RuntimeException("未知的区划类型");
            }
        } else {
            // 如果已有记录则将编码递增并进行超限判断
            ++typeNo;
            switch (typeId) {
                case 0:
                    if (typeNo < 1 || typeNo > 99) {
                        throw new RuntimeException("街道(地区)编码超限");
                    }
                    break;
                case 1:
                    if (typeNo < 100 || typeNo > 199) {
                        throw new RuntimeException("镇(民族镇)编码超限");
                    }
                    break;
                case 2:
                    if (typeNo < 200 || typeNo > 399) {
                        throw new RuntimeException("乡(民族乡、苏木)编码超限");
                    }
                    break;
                default: throw new RuntimeException("未知的区划类型");
            }
        }

        return typeNo;
    }
}
