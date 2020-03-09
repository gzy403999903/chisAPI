package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.Township;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TownshipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Township record);

    Township selectByPrimaryKey(Integer id);

    List<Township> selectAll();

    int updateByPrimaryKey(Township record);


    /* -------------------------------------------------------------------------------------------------------------- */

    Short selectMaxTypeNoByCriteria(@Param("typeId") Byte typeId,
                                    @Param("sysLocationId") Integer sysLocationId);

    List<Map<String, Object>> selectByCriteria(@Param("name") String name);

    List<Map<String, Object>> selectViewAll();

}
