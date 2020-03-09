package com.chisapp.modules.member.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.member.service.ExpendRecordService;
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
 * @Date: 2019/12/23 17:54
 * @Version 1.0
 */
@RequestMapping("/expendRecord")
@RestController
public class ExpendRecordHandler {

    private ExpendRecordService expendRecordService;
    @Autowired
    public void setExpendRecordService(ExpendRecordService expendRecordService) {
        this.expendRecordService = expendRecordService;
    }

    /**
     * 根据查询条件获取对应的记录
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param mrmMemberName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String mrmMemberName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                expendRecordService.getByCriteria(creationDate, lsh, mrmMemberName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }



}
