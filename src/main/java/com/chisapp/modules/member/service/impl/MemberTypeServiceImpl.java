package com.chisapp.modules.member.service.impl;

import com.chisapp.modules.member.bean.MemberType;
import com.chisapp.modules.member.dao.MemberTypeMapper;
import com.chisapp.modules.member.service.MemberTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/19 18:22
 * @Version 1.0
 */
@CacheConfig(cacheNames = "MemberType")
@Service
public class MemberTypeServiceImpl implements MemberTypeService {

    private MemberTypeMapper memberTypeMapper;
    @Autowired
    public void setMemberTypeMapper(MemberTypeMapper memberTypeMapper) {
        this.memberTypeMapper = memberTypeMapper;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "'enabled'"), // 删除开启状态的会员类型缓存
                    @CacheEvict(cacheNames = "Member", allEntries = true) // 删除会员缓存
            }
    )
    @Override
    public void save(MemberType memberType) {
        memberTypeMapper.insert(memberType);
    }

    @Caching(
            put = {
                    @CachePut(key = "#memberType.id")
            },
            evict = {
                    @CacheEvict(key = "'enabled'"),
                    @CacheEvict(cacheNames = "Member", allEntries = true) // 删除会员缓存
            }
    )
    @Override
    public MemberType update(MemberType memberType) {
        memberTypeMapper.updateByPrimaryKey(memberType);
        return memberType;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'enabled'"),
                    @CacheEvict(cacheNames = "Member", allEntries = true) // 删除会员缓存
            }
    )
    @Override
    public void delete(Integer id) {
        memberTypeMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(key = "#id")
    @Override
    public MemberType getById(Integer id) {
        return memberTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MemberType> getByCriteria(String name, Boolean state) {
        return memberTypeMapper.selectByCriteria(name, state);
    }

    @Cacheable(key = "'enabled'")
    @Override
    public List<MemberType> getEnabled() {
        return memberTypeMapper.selectEnabled();
    }


}
