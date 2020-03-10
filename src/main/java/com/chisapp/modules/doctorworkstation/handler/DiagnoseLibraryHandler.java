package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.doctorworkstation.service.DiagnoseLibraryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 13:11
 * @Version 1.0
 */
public class DiagnoseLibraryHandler {

    protected Integer DWT_DIAGNOSE_TYPE_ID = null; // 诊断类型ID

    private DiagnoseLibraryService diagnoseLibraryService;
    @Autowired
    public void setDiagnoseLibraryService(DiagnoseLibraryService diagnoseLibraryService) {
        this.diagnoseLibraryService = diagnoseLibraryService;
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param dwtDiagnoseClassifyId
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer dwtDiagnoseClassifyId,
            @RequestParam(required = false) String name){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = diagnoseLibraryService.getByCriteria(DWT_DIAGNOSE_TYPE_ID , dwtDiagnoseClassifyId, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


}
