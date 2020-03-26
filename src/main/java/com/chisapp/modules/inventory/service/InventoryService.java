package com.chisapp.modules.inventory.service;

import com.chisapp.modules.inventory.bean.Inventory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/9/29 16:23
 * @Version 1.0
 */
public interface InventoryService {

    /**
     * 入库操作
     * @param inventoryList
     */
    @Transactional
    void save(List<Inventory> inventoryList);

    /**
     * 减库存数量
     * (不要直接更新库存 要使用原库存减去对应库存 这样可在库存不足时直接抛出异常)
     * Inventory主要属性为 id 和 quantity
     * 辅助属性 gsmGoodsOid, gsmGoodsName, ph, pch
     * @param inventoryList
     */
    @Transactional
    void updateQuantityByList(List<Inventory> inventoryList);

    /**
     * 变更仓库
     * @param inventoryList
     */
    @Transactional
    void updateIymInventoryTypeIdByList(List<Inventory> inventoryList);

    /**
     * 拆零操作
     * @param id
     */
    @Transactional
    void splitQuantity(Integer id);

    /**
     * 获取对应商品库存的最后一条记录
     * (用于计算平均进价)
     * @param goodsIdList
     * @return
     */
    List<Inventory> getLastByGoodsIdList(List<Integer> goodsIdList);


    /**
     * 查询机构批次库存数量(用于检索)
     * @param sysClinicId 机构 ID
     * @param iymInventoryTypeId 仓库 ID
     * @param gsmGoodsName 商品名称
     * @return
     */
    List<Map<String, Object>> getClinicPchEnabledLikeByCriteria(Integer sysClinicId,
                                                                Integer iymInventoryTypeId,
                                                                String gsmGoodsName);

    /**
     * 查询机构批次库存数量(用于出库)[护士工作站]
     * @param sysClinicId
     * @param gsmGoodsIdList
     * @return
     */
    List<Map<String, Object>> getClinicPchListByGoodsIdList(Integer sysClinicId,
                                                            List<Integer> gsmGoodsIdList);

    /**
     * 查询机构退货批次库存数量(用于退货)
     * @param sysClinicId
     * @param iymInventoryTypeId
     * @param pemSupplierId
     * @param gsmGoodsName
     * @return
     */
    List<Map<String, Object>> getClinicSubtractPchLikeByCriteria(Integer sysClinicId,
                                                                 Integer iymInventoryTypeId,
                                                                 Integer pemSupplierId,
                                                                 String gsmGoodsName);

    /**
     * 获取对应的 ID 集合
     * @param idList
     * @return
     */
    List<Inventory> getByIdList(List<Integer> idList);

    /**
     * 获取本机构批号库存
     * @param sysClinicId 机构ID
     * @param iymInventoryTypeId 仓库ID
     * @param showZero 是否显示为 0 的库存
     * @param gsmGoodsOid 商品编码
     * @param gsmGoodsName 商品名称
     * @param ph 批号
     * @return
     */
    List<Map<String, Object>> getClinicPhGroupListByCriteria(Integer sysClinicId,
                                                             Integer iymInventoryTypeId,
                                                             Boolean showZero,
                                                             String gsmGoodsOid,
                                                             String gsmGoodsName,
                                                             String ph);

    /**
     * 获取全机构批号库存
     * @param showZero 是否显示为 0 的库存
     * @param sysClinicName 机构名称
     * @param gsmGoodsOid 商品编码
     * @param gsmGoodsName 商品名称
     * @param ph 批号
     * @return
     */
    List<Map<String, Object>> getPhGroupListByCriteria(Boolean showZero,
                                                       String sysClinicName,
                                                       String gsmGoodsOid,
                                                       String gsmGoodsName,
                                                       String ph);

    /**
     * 获取本机构批次库存(用于库存查询)
     * @param sysClinicId 机构ID
     * @param iymInventoryTypeId 仓库ID
     * @param showZero 是否显示为 0 的库存
     * @param gsmGoodsOid 商品编码
     * @param gsmGoodsName 商品名称
     * @param ph 批号
     * @return
     */
    List<Map<String, Object>> getClinicPchListByCriteria(Integer sysClinicId,
                                                         Integer iymInventoryTypeId,
                                                         Boolean showZero,
                                                         String gsmGoodsOid,
                                                         String gsmGoodsName,
                                                         String ph);

}
