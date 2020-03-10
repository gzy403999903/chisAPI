package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.modules.doctorworkstation.dao.DiagnoseLibraryMapper;
import com.chisapp.modules.doctorworkstation.service.DiagnoseLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 13:03
 * @Version 1.0
 */
@Service
public class DiagnoseLibraryServiceImpl implements DiagnoseLibraryService {

    private DiagnoseLibraryMapper diagnoseLibraryMapper;
    @Autowired
    public void setDiagnoseLibraryMapper(DiagnoseLibraryMapper diagnoseLibraryMapper) {
        this.diagnoseLibraryMapper = diagnoseLibraryMapper;
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer dwtDiagnoseTypeId, Integer dwtDiagnoseClassifyId, String name) {
        return diagnoseLibraryMapper.selectByCriteria(dwtDiagnoseTypeId, dwtDiagnoseClassifyId, name);
    }

}
