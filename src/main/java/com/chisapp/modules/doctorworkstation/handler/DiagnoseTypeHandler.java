package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.modules.doctorworkstation.bean.DiagnoseType;
import com.chisapp.modules.doctorworkstation.service.DiagnoseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 22:46
 * @Version 1.0
 */
@RequestMapping("/diagnoseType")
@RestController
public class DiagnoseTypeHandler {

    private DiagnoseTypeService diagnoseTypeService;
    @Autowired
    public void setDiagnoseTypeService(DiagnoseTypeService diagnoseTypeService) {
        this.diagnoseTypeService = diagnoseTypeService;
    }

    /**
     * 获取所有诊断类型
     * @return
     */
    @GetMapping("/getAll")
    public List<DiagnoseType> getAll() {
        return diagnoseTypeService.getAll();
    }

}
