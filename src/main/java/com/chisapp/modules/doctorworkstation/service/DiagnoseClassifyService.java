package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseClassify;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 11:15
 * @Version 1.0
 */
public interface DiagnoseClassifyService {
    List<DiagnoseClassify> getByDwtDiagnoseTypeId(Integer dwtDiagnoseTypeId);
}
