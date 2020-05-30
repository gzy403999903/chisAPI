package com.chisapp.modules.goods.service.impl;

import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.common.utils.KeyUtils;
import com.chisapp.modules.goods.bean.Equivalent;
import com.chisapp.modules.goods.dao.EquivalentMapper;
import com.chisapp.modules.goods.service.EquivalentService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-29 15:14
 * @Version 1.0
 */
@Service
public class EquivalentServiceImpl implements EquivalentService {
    @Autowired
    private EquivalentMapper equivalentMapper;

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveList(List<Equivalent> equivalentList) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取操作人信息

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        EquivalentMapper mapper = batchSqlSession.getMapper(EquivalentMapper.class);
        try {
            for (Equivalent equivalent : equivalentList) {
                equivalent.setCreatorId(user.getId());
                equivalent.setCreationDate(new Date());

                mapper.insert(equivalent);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public void save(Equivalent equivalent) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取操作人信息
        equivalent.setCreatorId(user.getId());
        equivalent.setCreationDate(new Date());
        equivalentMapper.insert(equivalent);
    }

    @Override
    public void update(Equivalent equivalent) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取操作人信息
        equivalent.setLastUpdaterId(user.getId());
        equivalent.setLastUpdateDate(new Date());

        equivalentMapper.updateByPrimaryKey(equivalent);
    }

    @Override
    public void delete(Integer id) {
        equivalentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Equivalent getById(Integer id) {
        return equivalentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String useGsmGoodsName, String referGsmGoodsName) {
        return equivalentMapper.selectByCriteria(useGsmGoodsName, referGsmGoodsName);
    }
}
