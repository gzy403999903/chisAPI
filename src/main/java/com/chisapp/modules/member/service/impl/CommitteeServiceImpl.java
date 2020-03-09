package com.chisapp.modules.member.service.impl;

import com.chisapp.modules.member.bean.Committee;
import com.chisapp.modules.member.dao.CommitteeMapper;
import com.chisapp.modules.member.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/10/21 14:21
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Committee")
@Service
public class CommitteeServiceImpl implements CommitteeService {

    private CommitteeMapper committeeMapper;
    @Autowired
    public void setCommitteeMapper(CommitteeMapper committeeMapper) {
        this.committeeMapper = committeeMapper;
    }

    @Override
    public void save(Committee committee) {
        committee.setTypeNo(this.getTypeNo(committee.getTypeId(), committee.getMrmTownshipId()));
        committeeMapper.insert(committee);
    }

    @CachePut(key = "#committee.id")
    @Override
    public Committee update(Committee committee) {
        committeeMapper.updateByPrimaryKey(committee);
        return committee;
    }

    @CacheEvict(key = "#id")
    @Override
    public void delete(Integer id) {
        committeeMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(key = "#id")
    @Override
    public Committee getById(Integer id) {
        return committeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Short getMaxTypeNoByCriteria(Byte typeId, Integer mrmTownshipId) {
        return committeeMapper.selectMaxTypeNoByCriteria(typeId, mrmTownshipId);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String mrmTownshipName, String name) {
        return committeeMapper.selectByCriteria(mrmTownshipName, name);
    }

    @Override
    public List<Map<String, Object>> getByMrmTownshipId(Integer mrmTownshipId) {
        return committeeMapper.selectByMrmTownshipId(mrmTownshipId);
    }

    @Override
    public Map<String, Object> getCommitteeMapById(Integer id) {
        return committeeMapper.selectCommitteeMapById(id);
    }


    /**
     * 获取最大的村(居委会)编码
     * @param typeId
     * @param mrmTownshipId
     * @return
     */
    private Short getTypeNo(Byte typeId, Integer mrmTownshipId) {
        // 获取最大编码
        short typeNo = this.getMaxTypeNoByCriteria(typeId, mrmTownshipId);

        // 如果没有查询到记录则根据编码设置初始值
        if (typeNo == 0) {
            switch (typeId) {
                case 0: typeNo = 1; break;
                case 2: typeNo = 200; break;
                default: throw new RuntimeException("未知的村(居委会)类型");
            }
        } else {
            // 编码进行递增 并判断是否超限
            ++typeNo;
            switch (typeId) {
                case 0:
                    if (typeNo < 0 || typeNo > 199) {
                        throw new RuntimeException("居民委员会编码范围超限");
                    }
                    break;
                case 2:
                    if (typeNo < 200 || typeNo > 399) {
                        throw new RuntimeException("村民委员会编码范围超限");
                    }
                    break;
                default: throw new RuntimeException("未知的村(居委会)类型");
            }
        }

        return typeNo;
    }
}
