package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.ShelfGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShelfGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShelfGoods record);

    ShelfGoods selectByPrimaryKey(Integer id);

    List<ShelfGoods> selectAll();

    int updateByPrimaryKey(ShelfGoods record);

    /* ------------------------------------------------------------------------------------------------------------- */

    void updateById(@Param("iymShelfPositionId") Integer iymShelfPositionId,
                    @Param("maxQuantity") Integer maxQuantity,
                    @Param("minQuantity") Integer minQuantity,
                    @Param("id") Integer id);

    List<Map<String, Object>> selectClinicListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                         @Param("gsmGoodsTypeId") Integer gsmGoodsTypeId,
                                                         @Param("gsmGoodsOid") String gsmGoodsOid,
                                                         @Param("gsmGoodsName") String gsmGoodsName,
                                                         @Param("iymShelfPositionName") String iymShelfPositionName);



}
