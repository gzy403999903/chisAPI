package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.PaymentWay;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/12/14 10:32
 * @Version 1.0
 */
public interface PaymentWayService {

    /**
     * 保存操作
     * @param paymentWay
     */
    @Transactional
    void save(PaymentWay paymentWay);

    /**
     * 修改操作
     * @param paymentWay
     * @return
     */
    @Transactional
    PaymentWay update(PaymentWay paymentWay);

    /**
     * 删除操作
     * @param id
     * @return
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    PaymentWay getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param name
     * @param state
     * @return
     */
    List<PaymentWay> getByCriteria(String name, Boolean state);

    /**
     * 获取所有对象集合
     * @return
     */
    List<PaymentWay> getEnabled();

}
