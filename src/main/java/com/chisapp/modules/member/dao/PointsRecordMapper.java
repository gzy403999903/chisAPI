package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.PointsRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PointsRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointsRecord record);

    PointsRecord selectByPrimaryKey(Integer id);

    List<PointsRecord> selectAll();

    int updateByPrimaryKey(PointsRecord record);



    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("lsh") String lsh,
                                               @Param("mrmMemberName") String mrmMemberName);

    Integer selectSumGivenPointsByLsh(@Param("lsh") String lsh);
}
