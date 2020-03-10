package com.chisapp.modules.inventory.service;

import com.chisapp.modules.inventory.bean.ShelfGoods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/5 15:07
 * @Version 1.0
 */
public interface ShelfGoodsService {

    /**
     * 保存或编辑集合操作
     * @param shelfGoodsList
     */
    @Transactional
    void saveOrUpdateList(List<ShelfGoods> shelfGoodsList);

    /**
     * 根据条件获取机构货位商品明细
     * @param sysClinicId
     * @param gsmGoodsTypeId
     * @param gsmGoodsName
     * @param iymShelfPositionName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(Integer sysClinicId,
                                                      Integer gsmGoodsTypeId,
                                                      String gsmGoodsName,
                                                      String iymShelfPositionName);

}
