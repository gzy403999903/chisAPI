package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.PerformItemRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PerformItemRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PerformItemRecord record);

    PerformItemRecord selectByPrimaryKey(Integer id);

    List<PerformItemRecord> selectAll();

    int updateByPrimaryKey(PerformItemRecord record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("mrmMemberName") String mrmMemberName,
                                               @Param("operatorName") String operatorName);
}
