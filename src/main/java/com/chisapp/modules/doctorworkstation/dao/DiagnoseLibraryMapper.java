package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseLibrary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DiagnoseLibraryMapper {
    int deleteByPrimaryKey(String oid);

    int insert(DiagnoseLibrary record);

    DiagnoseLibrary selectByPrimaryKey(String oid);

    List<DiagnoseLibrary> selectAll();

    int updateByPrimaryKey(DiagnoseLibrary record);

    /* -------------------------------------------------------------------------------------------------------------- */
    List<Map<String, Object>> selectByCriteria(@Param("dwtDiagnoseTypeId") Integer dwtDiagnoseTypeId,
                                               @Param("dwtDiagnoseClassifyId") Integer dwtDiagnoseClassifyId,
                                               @Param("name") String name);


}
