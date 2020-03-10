package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.PerformItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PerformItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PerformItem record);

    PerformItem selectByPrimaryKey(Integer id);

    List<PerformItem> selectAll();

    int updateByPrimaryKey(PerformItem record);

    /* -------------------------------------------------------------------------------------------------------------- */

    void updateResidueQuantityById(@Param("id") Integer id, @Param("performQuantity") Integer performQuantity);

    void updateResidueQuantityByList(@Param("performItemList") List<PerformItem> performItemList);

    void resetResidueQuantityByList(@Param("performItemList") List<PerformItem> performItemList);

    List<Map<String, Object>> selectByCriteria(@Param("mrmMemberId") Integer mrmMemberId,
                                               @Param("cimItemName") String cimItemName,
                                               @Param("showZero") Boolean showZero);
}
