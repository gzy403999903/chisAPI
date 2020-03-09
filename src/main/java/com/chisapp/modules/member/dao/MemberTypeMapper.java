package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.MemberType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberType record);

    MemberType selectByPrimaryKey(Integer id);

    List<MemberType> selectAll();

    int updateByPrimaryKey(MemberType record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<MemberType> selectByCriteria(@Param("name") String name, @Param("state") Boolean state);

    List<MemberType> selectEnabled();

}
