package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InventoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inventory record);

    Inventory selectByPrimaryKey(Integer id);

    List<Inventory> selectAll();

    int updateByPrimaryKey(Inventory record);

    /* -------------------------------------------------------------------------------------------------------------- */

    void updateQuantityById(@Param("id") Integer id, @Param("quantity") Integer quantity);

    void updateIymInventoryTypeIdById(@Param("iymInventoryTypeId") Integer iymInventoryTypeId,
                                      @Param("id") Integer id);

    List<Inventory> selectLastByGoodsIdList(@Param("goodsIdList") List<Integer> goodsIdList);

    List<Map<String, Object>> selectClinicPchEnabledLikeByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                                   @Param("iymInventoryTypeId") Integer iymInventoryTypeId,
                                                                   @Param("gsmGoodsName") String gsmGoodsName);

    List<Map<String, Object>> selectClinicPchListByGoodsIdList(@Param("sysClinicId") Integer sysClinicId,
                                                               @Param("gsmGoodsIdList") List<Integer> gsmGoodsIdList);

    List<Map<String, Object>> selectClinicSubtractPchLikeByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                                    @Param("iymInventoryTypeId") Integer iymInventoryTypeId,
                                                                    @Param("pemSupplierId") Integer pemSupplierId,
                                                                    @Param("gsmGoodsName") String gsmGoodsName);

    List<Map<String, Object>> selectByIdList(@Param("idList") List<Integer> idList);

    List<Map<String, Object>> selectPhGroupListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                          @Param("iymInventoryTypeId") Integer iymInventoryTypeId,
                                                          @Param("showZero") Boolean showZero,
                                                          @Param("sysClinicName") String sysClinicName,
                                                          @Param("gsmGoodsName") String gsmGoodsName,
                                                          @Param("ph") String ph);

    List<Map<String, Object>> selectClinicPchListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                            @Param("iymInventoryTypeId") Integer iymInventoryTypeId,
                                                            @Param("showZero") Boolean showZero,
                                                            @Param("gsmGoodsName") String gsmGoodsName,
                                                            @Param("ph") String ph);

}
