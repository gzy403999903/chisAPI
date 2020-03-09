package com.chisapp.modules.item.dao;

import com.chisapp.modules.item.bean.ItemApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemApply record);

    ItemApply selectByPrimaryKey(Integer id);

    List<ItemApply> selectAll();

    int updateByPrimaryKey(ItemApply record);

    /*----------------------------------------------------------------------------------------------------------------*/

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("approveState") Byte approveState,
                                               @Param("name") String name);

}
