package com.chisapp.modules.item.dao;

import com.chisapp.modules.item.bean.ItemAdjustPrice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItemAdjustPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemAdjustPrice record);

    ItemAdjustPrice selectByPrimaryKey(Integer id);

    List<ItemAdjustPrice> selectAll();

    int updateByPrimaryKey(ItemAdjustPrice record);

    /*----------------------------------------------------------------------------------------------------------------*/
    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);

    void updateByLsh(@Param("lsh") String lsh,
                     @Param("approverId") Integer approverId,
                     @Param("approveDate") Date approveDate,
                     @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("name") String name,
                                               @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                        @Param("lsh") String lsh,
                                                        @Param("approveState") Byte approveState);
}
