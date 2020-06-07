package com.chisapp.modules.purchase.service;

import com.chisapp.modules.purchase.bean.AssessCost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-06-04 11:24
 * @Version 1.0
 */
public interface AssessCostService {

    /**
     * 保存操作
     * @param assessCost
     */
    @Transactional
    void save(AssessCost assessCost);

    /**
     * 编辑操作
     * @param assessCost
     */
    @Transactional
    void update(AssessCost assessCost);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    AssessCost getById(Integer id);

    /**
     * 根据供应商ID 和 商品ID集合获取对应的考核成本集合
     * @param pemSupplierId
     * @param gsmGoodsIdList
     * @return
     */
    List<AssessCost> getBySupplierIdAndGoodsIdList(Integer pemSupplierId,
                                                   List<Integer> gsmGoodsIdList);

    /**
     * 根据条件获取对应的集合
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @return
     */
    List<Map<String, Object>> getByCriteria(String pemSupplierOid,
                                            String pemSupplierName,
                                            String gsmGoodsOid,
                                            String gsmGoodsName);

}
