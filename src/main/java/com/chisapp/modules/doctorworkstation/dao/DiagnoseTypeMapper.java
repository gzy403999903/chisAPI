package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseType;
import java.util.List;

public interface DiagnoseTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiagnoseType record);

    DiagnoseType selectByPrimaryKey(Integer id);

    List<DiagnoseType> selectAll();

    int updateByPrimaryKey(DiagnoseType record);
}