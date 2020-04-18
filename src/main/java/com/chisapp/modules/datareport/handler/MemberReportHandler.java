package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.InventoryAddReportService;
import com.chisapp.modules.datareport.service.MemberReportService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-14 23:09
 * @Version 1.0
 */
@RequestMapping("/memberReport")
@RestController
public class MemberReportHandler {

    private MemberReportService memberReportService;
    @Autowired
    public void setMemberReportService(MemberReportService memberReportService) {
        this.memberReportService = memberReportService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 会员分析
     * @param pageNum
     * @param pageSize
     * @param sellDays
     * @param oid
     * @param name
     * @param phone
     * @param age
     * @param birthSurplusDays
     * @param sysClinicName
     * @param balance
     * @param czpc
     * @param czhj
     * @param xfpc
     * @param xfhj
     * @return
     */
    @GetMapping("/getMemberAnalysisByCriteria")
    public PageResult getMemberAnalysisByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "30") Integer sellDays,
            @RequestParam(required = false) String oid,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String birthSurplusDays,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String balance,
            @RequestParam(required = false) String czpc,
            @RequestParam(required = false) String czhj,
            @RequestParam(required = false) String xfpc,
            @RequestParam(required = false) String xfhj){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = memberReportService.getMemberAnalysisByCriteria(sellDays, oid, name,
                phone, age, birthSurplusDays, sysClinicName, balance, czpc, czhj, xfpc, xfhj);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

}
