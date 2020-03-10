package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseClassify;
import com.chisapp.modules.doctorworkstation.service.DiagnoseClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 11:58
 * @Version 1.0
 */
public class DiagnoseClassifyHandler {

    protected Integer DWT_DIAGNOSE_TYPE_ID = null;

    private DiagnoseClassifyService diagnoseClassifyService;
    @Autowired
    public void setDiagnoseClassifyService(DiagnoseClassifyService diagnoseClassifyService) {
        this.diagnoseClassifyService = diagnoseClassifyService;
    }

    @GetMapping("/getAll")
    public List<DiagnoseClassify> getAll() {
        return diagnoseClassifyService.getByDwtDiagnoseTypeId(DWT_DIAGNOSE_TYPE_ID);
    }


}
