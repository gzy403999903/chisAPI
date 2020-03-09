package com.chisapp.modules.goods.dao;

import com.chisapp.modules.goods.bean.GoodsAdjustPrice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GoodsAdjustPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsAdjustPrice record);

    GoodsAdjustPrice selectByPrimaryKey(Integer id);

    List<GoodsAdjustPrice> selectAll();

    int updateByPrimaryKey(GoodsAdjustPrice record);

    /*----------------------------------------------------------------------------------------------------------------*/
    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);

    int updateByLsh(@Param("lsh") String lsh,
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
