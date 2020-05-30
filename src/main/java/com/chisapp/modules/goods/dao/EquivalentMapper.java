package com.chisapp.modules.goods.dao;

import com.chisapp.modules.goods.bean.Equivalent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EquivalentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Equivalent record);

    Equivalent selectByPrimaryKey(Integer id);

    List<Equivalent> selectAll();

    int updateByPrimaryKey(Equivalent record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("useGsmGoodsName") String useGsmGoodsName,
                                               @Param("referGsmGoodsName") String referGsmGoodsName);











}
