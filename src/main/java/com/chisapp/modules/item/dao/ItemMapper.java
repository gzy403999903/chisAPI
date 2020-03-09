package com.chisapp.modules.item.dao;

import com.chisapp.modules.item.bean.Item;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();

    int updateByPrimaryKey(Item record);

    /*----------------------------------------------------------------------------------------------------------------*/

    void updateRetailPriceById(@Param("retailPrice") BigDecimal retailPrice, @Param("id") Integer id);

    List<Map<String, Object>> selectByCriteria(@Param("cimItemTypeId") Integer cimItemTypeId,
                                               @Param("state") Boolean state,
                                               @Param("itemClassifyId") Integer itemClassifyId,
                                               @Param("ybItem") Boolean ybItem,
                                               @Param("name") String name);

    List<Map<String, Object>> selectEnabledLikeByName(@Param("name") String name);

    List<Map<String, Object>> selectEnabledLikeByNameForPrescription(@Param("cimItemTypeId") Integer cimItemTypeId,
                                                                     @Param("name") String name);

    List<Item> selectEnabledByBillingTypeId(@Param("billingTypeId") Integer billingTypeId);

}
