package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.MemberHealth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberHealthMapper {
    int insert(MemberHealth record);

    List<MemberHealth> selectAll();

    /* -------------------------------------------------------------------------------------------------------------- */
    void deleteByMemberId(@Param("mrmMemberId") Integer mrmMemberId);

    List<MemberHealth> selectByMrmMemberId(@Param("mrmMemberId") Integer mrmMemberId);

}
