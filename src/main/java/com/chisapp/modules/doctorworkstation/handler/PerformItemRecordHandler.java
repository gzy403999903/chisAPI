package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.doctorworkstation.service.PerformItemRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/28 23:33
 * @Version 1.0
 */
@RequestMapping("/performItemRecord")
@RestController
public class PerformItemRecordHandler {

    private PerformItemRecordService performItemRecordService;
    @Autowired
    public void setPerformItemRecordService(PerformItemRecordService performItemRecordService) {
        this.performItemRecordService = performItemRecordService;
    }

    /**
     * 根据查询条件获取对应的集合
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param mrmMemberName
     * @param operatorName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) String operatorName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                performItemRecordService.getByCriteria(creationDate, mrmMemberName, operatorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }




}
