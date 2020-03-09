package com.chisapp.modules.item.service.impl;

import com.chisapp.modules.item.bean.Item;
import com.chisapp.modules.item.dao.ItemMapper;
import com.chisapp.modules.item.service.ItemService;
import com.chisapp.modules.system.bean.User;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/6 17:25
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Item")
@Service
public class ItemServiceImpl implements ItemService {

    private ItemMapper itemMapper;
    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /* --------------------------------------------------------------------------------------------------------------- */

    @CacheEvict(key = "'billingTypeId' + #item.billingTypeId")
    @Override
    public void save(Item item) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        item.setCreatorId(user.getId());
        item.setCreationDate(new Date());
        itemMapper.insert(item);
    }

    @Caching(
            put = {
                    @CachePut(key = "#item.id")
            },
            evict = {
                    @CacheEvict(key = "'billingTypeId' + #item.billingTypeId")
            }
    )
    @Override
    public Item update(Item item) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        item.setLastUpdaterId(user.getId());
        item.setLastUpdateDate(new Date());
        itemMapper.updateByPrimaryKey(item);
        return item;
    }

    @Override
    public void updateRetailPriceByList(List<Item> itemList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ItemMapper mapper = batchSqlSession.getMapper(ItemMapper.class);
        try {
            for (Item item : itemList) {
                mapper.updateRetailPriceById(item.getRetailPrice(), item.getId());
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#item.id"),
                    @CacheEvict(key = "'billingTypeId' + #item.billingTypeId")
            }
    )
    @Override
    public void cacheEvictById(Item item) {
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#item.id"),
                    @CacheEvict(key = "'billingTypeId' + #item.billingTypeId")
            }
    )
    @Override
    public Item delete(Item item) {
        itemMapper.deleteByPrimaryKey(item.getId());
        return item;
    }

    @Cacheable(key = "#id")
    @Override
    public Item getById(Integer id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer cimItemTypeId, Boolean state, Integer itemClassifyId, Boolean ybItem, String name) {
        return itemMapper.selectByCriteria(cimItemTypeId, state, itemClassifyId, ybItem, name);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByName(String name) {
        return itemMapper.selectEnabledLikeByName(name);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByNameForPrescription(Integer cimItemTypeId, String name) {
        return itemMapper.selectEnabledLikeByNameForPrescription(cimItemTypeId, name);
    }

    @Cacheable(key = "'billingTypeId' + #billingTypeId")
    @Override
    public List<Item> getEnabledByBillingTypeId(Integer billingTypeId) {
        return itemMapper.selectEnabledByBillingTypeId(billingTypeId);
    }
}
