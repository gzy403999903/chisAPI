package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.SecondClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecondClassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecondClassify record);

    SecondClassify selectByPrimaryKey(Integer id);

    List<SecondClassify> selectAll();

    int updateByPrimaryKey(SecondClassify record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("goodsClassifyId") Integer goodsClassifyId,
                                               @Param("name") String name,
                                               @Param("state") Boolean state);

    List<Map<String, Object>> selectEnabledByGoodsClassifyId(@Param("goodsClassifyId") Integer goodsClassifyId);


}
