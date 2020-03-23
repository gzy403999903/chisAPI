package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.PracticeScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PracticeScopeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PracticeScope record);

    PracticeScope selectByPrimaryKey(Integer id);

    List<PracticeScope> selectAll();

    int updateByPrimaryKey(PracticeScope record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("practiceTypeId") Integer practiceTypeId,
                                               @Param("name") String name);

    List<PracticeScope> selectByPracticeTypeId(@Param("practiceTypeId") Integer practiceTypeId);
}
