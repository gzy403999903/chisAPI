package com.chisapp.modules.goods.dao;

import com.chisapp.modules.goods.bean.GoodsApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodsApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsApply record);

    GoodsApply selectByPrimaryKey(Integer id);

    List<GoodsApply> selectAll();

    int updateByPrimaryKey(GoodsApply record);

    /*----------------------------------------------------------------------------------------------------------------*/

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("approveState") Byte approveState,
                                               @Param("name") String name);

}
