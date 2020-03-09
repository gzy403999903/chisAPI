package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.ExpendRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExpendRecord record);

    ExpendRecord selectByPrimaryKey(Integer id);

    List<ExpendRecord> selectAll();

    int updateByPrimaryKey(ExpendRecord record);


    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("lsh") String lsh,
                                               @Param("mrmMemberName") String mrmMemberName);
}
