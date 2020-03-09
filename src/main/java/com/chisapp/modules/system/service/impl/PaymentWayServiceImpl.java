package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.PaymentWay;
import com.chisapp.modules.system.dao.PaymentWayMapper;
import com.chisapp.modules.system.service.PaymentWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/12/14 10:48
 * @Version 1.0
 */
@CacheConfig(cacheNames = "PaymentWay")
@Service
public class PaymentWayServiceImpl implements PaymentWayService {
    private PaymentWayMapper paymentWayMapper;
    @Autowired
    public void setPaymentWayMapper(PaymentWayMapper paymentWayMapper) {
        this.paymentWayMapper = paymentWayMapper;
    }

    @CacheEvict(key = "'enabled'")
    @Override
    public void save(PaymentWay paymentWay) {
        paymentWayMapper.insert(paymentWay);
    }

    @Caching(
            put = {
                    @CachePut(key = "#paymentWay.id")
            },
            evict = {
                    @CacheEvict(key = "'enabled'")
            }
    )
    @Override
    public PaymentWay update(PaymentWay paymentWay) {
        paymentWayMapper.updateByPrimaryKey(paymentWay);
        return paymentWay;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'enabled'")
            }
    )
    @Override
    public void delete(Integer id) {
        paymentWayMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(key = "#id")
    @Override
    public PaymentWay getById(Integer id) {
        return paymentWayMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PaymentWay> getByCriteria(String name, Boolean state) {
        return paymentWayMapper.selectByCriteria(name, state);
    }

    @Cacheable(key = "'enabled'")
    @Override
    public List<PaymentWay> getEnabled() {
        return paymentWayMapper.selectEnabled();
    }
}
