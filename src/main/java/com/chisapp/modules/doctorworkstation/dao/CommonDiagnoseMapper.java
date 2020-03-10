package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.CommonDiagnose;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommonDiagnoseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDiagnose record);

    CommonDiagnose selectByPrimaryKey(Integer id);

    List<CommonDiagnose> selectAll();

    int updateByPrimaryKey(CommonDiagnose record);


    /* -------------------------------------------------------------------------------------------------------------- */
    List<Map<String, Object>> selectByCriteria(@Param("creatorId") Integer creatorId,
                                               @Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId,
                                               @Param("shareState") Boolean shareState,
                                               @Param("name") String name);

    List<CommonDiagnose> selectLikeByName(@Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId,
                                          @Param("name") String name);



}
