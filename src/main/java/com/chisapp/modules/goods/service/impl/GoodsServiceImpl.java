package com.chisapp.modules.goods.service.impl;

import com.chisapp.modules.goods.bean.Goods;
import com.chisapp.modules.goods.dao.GoodsMapper;
import com.chisapp.modules.goods.service.GoodsService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/19 15:38
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Goods")
@Service
public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(Goods goods) {
        if (!splitValidate(goods)) {
            throw new RuntimeException("拆分属性未按要求填写");
        }

        if (!westernDrugsValidate(goods)) {
            throw new RuntimeException("西药属性未按要求填写");
        }

        if (goods.getCreatorId() == null) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            goods.setCreatorId(user.getId());
            goods.setCreationDate(new Date());
        }
        goodsMapper.insert(goods);
    }

    @CachePut(key = "#goods.id")
    @Override
    public Goods update(Goods goods) {
        if (!splitValidate(goods)) {
            throw new RuntimeException("拆分属性未按要求填写");
        }

        if (!westernDrugsValidate(goods)) {
            throw new RuntimeException("西药属性未按要求填写");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        goods.setLastUpdaterId(user.getId());
        goods.setLastUpdateDate(new Date());
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

    @Override
    public void updateRetailPriceByList(List<Goods> goodsList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        GoodsMapper mapper = batchSqlSession.getMapper(GoodsMapper.class);
        try {
            for (Goods goods : goodsList) {
                mapper.updateRetailPriceById(goods.getRetailPrice(), goods.getSplitPrice(), goods.getId());
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @CacheEvict(key = "#id")
    @Override
    public void cacheEvictById(Integer id) {
    }

    @CacheEvict(key = "#goods.id")
    @Override
    public Goods delete(Goods goods) {
        goodsMapper.deleteByPrimaryKey(goods.getId());
        return goods;
    }

    @Cacheable(key = "#id")
    @Override
    public Goods getById(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String oid, Integer gsmGoodsTypeId ,Integer goodsClassifyId, Boolean state, String name) {
        return goodsMapper.selectByCriteria(oid, gsmGoodsTypeId, goodsClassifyId, state, name);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByName(String name) {
        return goodsMapper.selectEnabledLikeByName(name);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByNameForPlan(Integer sysClinicId, String name) {
        return goodsMapper.selectEnabledLikeByNameForPlan(sysClinicId, name);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByNameForPrescription(Integer sysClinicId, Integer gsmGoodsTypeId, String name) {
        return goodsMapper.selectEnabledLikeByNameForPrescription(sysClinicId, gsmGoodsTypeId, name);
    }
}
