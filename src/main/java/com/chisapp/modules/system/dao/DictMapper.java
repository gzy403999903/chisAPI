package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    Dict selectByPrimaryKey(Integer id);

    List<Dict> selectAll();

    int updateByPrimaryKey(Dict record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<Dict> selectByCriteria(@Param("name") String name,
                                @Param("sysDictTypeId") Integer sysDictTypeId,
                                @Param("state") Boolean state);

    List<Dict> selectEnabledLikeByName(@Param("sysDictTypeId") Integer sysDictTypeId,
                                       @Param("name") String name);

    List<Dict> selectEnabledByTypeId(@Param("sysDictTypeId") Integer sysDictTypeId);
}
