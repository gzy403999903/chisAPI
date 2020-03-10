package com.chisapp.modules.purchase.service.impl;

import com.chisapp.modules.purchase.bean.Supplier;
import com.chisapp.modules.purchase.dao.SupplierMapper;
import com.chisapp.modules.purchase.service.SupplierService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/23 16:01
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Supplier")
@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierMapper supplierMapper;
    @Autowired
    public void setSupplierMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    @Override
    public void save(Supplier supplier) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        supplier.setCreatorId(user.getId());
        supplier.setCreationDate(new Date());

        supplierMapper.insert(supplier);
    }

    @CachePut(key = "#supplier.id")
    @Override
    public Supplier update(Supplier supplier) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        supplier.setLastUpdaterId(user.getId());
        supplier.setLastUpdateDate(new Date());

        supplierMapper.updateByPrimaryKey(supplier);
        return supplier;
    }

    @CachePut(key = "#id")
    @Override
    public Supplier addArrearagesAmount(Integer id, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("0")) < 0) {
            throw new RuntimeException("付款金额不能小于 0");
        }
        // 更新缓存中的欠款金额
        Supplier supplier = this.getById(id);
        BigDecimal arrearagesAmount = supplier.getArrearagesAmount().add(amount);
        supplier.setArrearagesAmount(arrearagesAmount);
        // 更新数据库中的欠款金额
        supplierMapper.addArrearagesAmount(id, amount);
        return supplier;
    }

    @CachePut(key = "#id")
    @Override
    public Supplier subtractArrearagesAmount(Integer id, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("0")) < 0) {
            throw new RuntimeException("付款金额不能小于 0");
        }
        // 更新缓存中的欠款金额
        Supplier supplier = this.getById(id);
        BigDecimal arrearagesAmount = supplier.getArrearagesAmount().subtract(amount);
        if (arrearagesAmount.compareTo(new BigDecimal("0")) < 0) {
            throw new RuntimeException("付款金额不能大于供应商未付总金额");
        }
        supplier.setArrearagesAmount(arrearagesAmount);
        // 更新数据库中的欠款金额
        supplierMapper.subtractArrearagesAmount(id, amount);
        return supplier;
    }

    @CacheEvict(key = "#supplier.id")
    @Override
    public Supplier delete(Supplier supplier) {
        supplierMapper.deleteByPrimaryKey(supplier.getId());
        return supplier;
    }

    @Cacheable(key = "#id")
    @Override
    public Supplier getById(Integer id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String name, String contacterPhone, Boolean state) {
        return supplierMapper.selectByCriteria(name, contacterPhone, state,null, null, null);
    }

    @Override
    public List<Map<String, Object>> getByCriteriaForAccount(String name, BigDecimal arrearagesAmount, BigDecimal arrearagesLimit, Integer arrearagesDays) {
        return supplierMapper.selectByCriteria(name, null, null,arrearagesAmount, arrearagesLimit, arrearagesDays);
    }

    @Override
    public List<Map<String, Object>> getEnabledLikeByName(String name) {
        return supplierMapper.selectEnabledLikeByName(name);
    }
}
