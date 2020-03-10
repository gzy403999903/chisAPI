package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseClassify;
import com.chisapp.modules.doctorworkstation.dao.DiagnoseClassifyMapper;
import com.chisapp.modules.doctorworkstation.service.DiagnoseClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 11:22
 * @Version 1.0
 */
@CacheConfig(cacheNames = "DiagnoseClassify")
@Service
public class DiagnoseClassifyServiceImpl implements DiagnoseClassifyService {

    private DiagnoseClassifyMapper diagnoseClassifyMapper;
    @Autowired
    public void setDiagnoseClassifyMapper(DiagnoseClassifyMapper diagnoseClassifyMapper) {
        this.diagnoseClassifyMapper = diagnoseClassifyMapper;
    }

    @Cacheable(key = "#dwtDiagnoseTypeId")
    @Override
    public List<DiagnoseClassify> getByDwtDiagnoseTypeId(Integer dwtDiagnoseTypeId) {
        return diagnoseClassifyMapper.selectByDwtDiagnoseTypeId(dwtDiagnoseTypeId);
    }
}
