package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiagnoseClassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiagnoseClassify record);

    DiagnoseClassify selectByPrimaryKey(Integer id);

    List<DiagnoseClassify> selectAll();

    int updateByPrimaryKey(DiagnoseClassify record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<DiagnoseClassify> selectByDwtDiagnoseTypeId(@Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId);
}
