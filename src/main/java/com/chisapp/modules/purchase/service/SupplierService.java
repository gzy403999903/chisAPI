package com.chisapp.modules.purchase.service;

import com.chisapp.modules.purchase.bean.Supplier;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/23 15:58
 * @Version 1.0
 */
public interface SupplierService {

    /**
     * 保存操作
     * @param supplier
     */
    @Transactional
    void save(Supplier supplier);

    /**
     * 编辑操作
     * @param supplier
     * @return
     */
    @Transactional
    Supplier update(Supplier supplier);

    /**
     * 增加欠款金额
     * 不能直接更新, 要使用原欠款金额 + 传入的金额
     * @param id
     * @param amount
     */
    @Transactional
    Supplier addArrearagesAmount(Integer id, BigDecimal amount);

    /**
     * 扣减欠款金额
     * 不能直接更新, 要使用原欠款金额 - 传入的金额
     * @param id
     * @param amount
     * @return
     */
    @Transactional
    Supplier subtractArrearagesAmount(Integer id, BigDecimal amount);

    /**
     * 删除操作
     * @param supplier
     * @return
     */
    @Transactional
    Supplier delete(Supplier supplier);

    /**
     * 根据主键 ID 获取对象
     * @param id
     * @return
     */
    Supplier getById(Integer id);

    /**
     * 根据查询条件 获取对应视图对象的集合
     * @param name
     * @param contacterPhone
     * @param state
     * @return
     */
    List<Map<String, Object>> getByCriteria(String name, String contacterPhone, Boolean state);

    /**
     * 根据条件获取供应商集合
     * [供应商账款调用]
     * @param name
     * @param arrearagesAmount
     * @param arrearagesLimit
     * @param arrearagesDays
     * @return
     */
    List<Map<String, Object>> getByCriteriaForAccount(String name, BigDecimal arrearagesAmount, BigDecimal arrearagesLimit, Integer arrearagesDays);

    /**
     * 根据 通用名称 或 助记码获取对应集合
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByName(String name);


}
