package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.WorkGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkGroup record);

    WorkGroup selectByPrimaryKey(Integer id);

    List<WorkGroup> selectAll();

    int updateByPrimaryKey(WorkGroup record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<WorkGroup> selectByCriteria(@Param("name") String name,
                                     @Param("state") Boolean state);

    List<WorkGroup> selectEnabled();
}
