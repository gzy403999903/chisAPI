package com.chisapp.modules.goods.service;

import com.chisapp.common.enums.GoodsTypeEnum;
import com.chisapp.modules.goods.bean.Goods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/19 15:03
 * @Version 1.0
 */
public interface GoodsService {

    /**
     * 保存操作
     * @param goods
     */
    @Transactional
    void save(Goods goods);

    /**
     * 编辑操作
     * @param goods
     * @return
     */
    @Transactional
    Goods update(Goods goods);

    /**
     * 批量调价
     * 必须封装 Goods.id 和 Goods.retailPrice 属性, 不能为 null
     * 调用 cacheEvictById 清除缓存
     * @param goodsList
     */
    @Transactional
    void updateRetailPriceByList(List<Goods> goodsList);

    /***
     * 根据 id 删除对应的缓存
     * 这个方法没有任何实现, 仅为删除对应缓存
     * @param id
     */
    void cacheEvictById(Integer id);


    /**
     * 删除操作
     * @param goods
     * @return
     */
    @Transactional
    Goods delete(Goods goods);

    /**
     * 根据主键 ID 获取对象
     * @param id
     * @return
     */
    Goods getById(Integer id);

    /**
     * 根据查询条件 获取对应视图对象的集合
     * @param oid
     * @param gsmGoodsTypeId
     * @param goodsClassifyId
     * @param state
     * @param name
     * @return
     */
    List<Map<String, Object>> getByCriteria(String oid, Integer gsmGoodsTypeId, Integer goodsClassifyId, Boolean state, String name);

    /**
     * 根据 通用名称 或 助记码获取对应集合
     * (调价功能调用)
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByName(String name);

    /**
     * 采购计划调用 检索对应机构对应商品
     * @param sysClinicId
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByNameForPlan(Integer sysClinicId, String name);

    /**
     * 开具处方调用 检索对应机构对应商品
     * @param sysClinicId
     * @param gsmGoodsTypeId
     * @param name
     * @return
     */
    List<Map<String, Object>> getEnabledLikeByNameForPrescription(Integer sysClinicId, Integer gsmGoodsTypeId, String name);

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * 验证拆分属性
     * @param goods
     * @return
     */
    default Boolean splitValidate(Goods goods) {
        if (!goods.getSplitable()) {
            return true;
        }

        if (goods.getSplitQuantity() == null ||
            goods.getSplitUnitsId() == null ||
            goods.getSplitPrice() == null) {
            return false;
        }
        return true;
    }

    /**
     * 验证西药属性
     * @param goods
     * @return
     */
    default Boolean westernDrugsValidate(Goods goods) {
        if (goods.getGsmGoodsTypeId() != GoodsTypeEnum.WESTERN_DRUGS.getIndex()) {
            return true;
        }

        if (goods.getDoseTypeId() == null ||
            goods.getDose() == null ||
            goods.getDoseUnitsId() == null ||
            goods.getDrugUsageId() == null ||
            goods.getApprovalNum() == null ||
            goods.getApprovalNumExpiryDate() == null ) {
            return false;
        }

        return true;
    }

}
