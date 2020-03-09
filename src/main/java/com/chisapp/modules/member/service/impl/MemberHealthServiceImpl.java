package com.chisapp.modules.member.service.impl;

import com.chisapp.modules.member.bean.MemberHealth;
import com.chisapp.modules.member.dao.MemberHealthMapper;
import com.chisapp.modules.member.service.MemberHealthService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/10/24 15:45
 * @Version 1.0
 */
@CacheConfig(cacheNames = "MemberHealth")
@Service
public class MemberHealthServiceImpl implements MemberHealthService {

    private MemberHealthMapper memberHealthMapper;
    @Autowired
    public void setMemberHealthMapper(MemberHealthMapper memberHealthMapper) {
        this.memberHealthMapper = memberHealthMapper;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(Integer mrmMemberId, List<MemberHealth> memberHealthList) {
        // 清空之前的记录
        this.delete(mrmMemberId);
        // 保存
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        MemberHealthMapper mapper = batchSqlSession.getMapper(MemberHealthMapper.class);
        try {
            for (MemberHealth memberHealth : memberHealthList) {
                mapper.insert(memberHealth);
            }

            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @CacheEvict(key = "#mrmMemberId")
    @Override
    public void delete(Integer mrmMemberId) {
        memberHealthMapper.deleteByMemberId(mrmMemberId);
    }

    @Cacheable(key = "#mrmMemberId")
    @Override
    public List<MemberHealth> getByMrmMemberId(Integer mrmMemberId) {
        return memberHealthMapper.selectByMrmMemberId(mrmMemberId);
    }

}
