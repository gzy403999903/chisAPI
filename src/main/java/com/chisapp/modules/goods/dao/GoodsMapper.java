package com.chisapp.modules.goods.dao;

import com.chisapp.modules.goods.bean.Goods;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Integer id);

    List<Goods> selectAll();

    int updateByPrimaryKey(Goods record);


    /*---------------------------------------------------------------------------------------------------------------*/

    void updateRetailPriceById(@Param("retailPrice") BigDecimal retailPrice,
                               @Param("splitPrice") BigDecimal splitPrice,
                               @Param("id") Integer id);

    List<Map<String, Object>> selectByCriteria(@Param("oid") String oid,
                                               @Param("gsmGoodsTypeId") Integer gsmGoodsTypeId,
                                               @Param("goodsClassifyId") Integer goodsClassifyId,
                                               @Param("state") Boolean state,
                                               @Param("name") String name);

    List<Map<String, Object>> selectEnabledLikeByName(@Param("name") String name);

    List<Map<String, Object>> selectEnabledLikeByNameForPlan(@Param("sysClinicId") Integer sysClinicId,
                                                             @Param("name") String name);

    List<Map<String, Object>> selectEnabledLikeByNameForPrescription(@Param("sysClinicId") Integer sysClinicId,
                                                                     @Param("gsmGoodsTypeId") Integer gsmGoodsTypeId,
                                                                     @Param("name") String name);
}
