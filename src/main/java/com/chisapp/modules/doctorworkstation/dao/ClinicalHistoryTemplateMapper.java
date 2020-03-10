package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.ClinicalHistoryTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClinicalHistoryTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClinicalHistoryTemplate record);

    ClinicalHistoryTemplate selectByPrimaryKey(Integer id);

    List<ClinicalHistoryTemplate> selectAll();

    int updateByPrimaryKey(ClinicalHistoryTemplate record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectByCriteria(@Param("sysDoctorId") Integer sysDoctorId,
                                               @Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId,
                                               @Param("shareState") Boolean shareState,
                                               @Param("name") String name);
}
