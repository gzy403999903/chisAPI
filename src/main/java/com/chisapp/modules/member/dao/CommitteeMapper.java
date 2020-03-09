package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.Committee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommitteeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Committee record);

    Committee selectByPrimaryKey(Integer id);

    List<Committee> selectAll();

    int updateByPrimaryKey(Committee record);

    /* -------------------------------------------------------------------------------------------------------------- */

    Short selectMaxTypeNoByCriteria(@Param("typeId") Byte typeId,
                                    @Param("mrmTownshipId") Integer mrmTownshipId);

    List<Map<String, Object>> selectByCriteria(@Param("mrmTownshipName") String mrmTownshipName,
                                               @Param("name") String name);

    List<Map<String, Object>> selectByMrmTownshipId(@Param("mrmTownshipId") Integer mrmTownshipId);

    Map<String, Object> selectCommitteeMapById(@Param("id") Integer id);

}
