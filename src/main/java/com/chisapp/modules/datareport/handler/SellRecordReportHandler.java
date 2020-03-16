package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.SellRecordReportService;
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
 * @Date: 2020-03-15 16:43
 * @Version 1.0
 */
@RequestMapping("/sellRecordReport")
@RestController
public class SellRecordReportHandler {

    private SellRecordReportService sellRecordReportService;
    @Autowired
    public void setSellRecordReportService(SellRecordReportService sellRecordReportService) {
        this.sellRecordReportService = sellRecordReportService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取机构对应的销售明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param sellerName
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String sellerName){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getByCriteria(user.getSysClinicId(), creationDate, lsh, sellerName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取所有机构对应的销售明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param sellerName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String sellerName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getByCriteria(null, creationDate, lsh, sellerName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 计费类型汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @return
     */
    @GetMapping("/getBillingTypeGroupListByCriteria")
    public PageResult getBillingTypeGroupListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                         @RequestParam(defaultValue="1") Integer pageSize,
                                                         @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                         @RequestParam(required = false) String sysClinicName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getBillingTypeGroupListByCriteria(creationDate, null, sysClinicName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 计费类型汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicBillingTypeGroupListByCriteria")
    public PageResult getClinicBillingTypeGroupListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                               @RequestParam(defaultValue="1") Integer pageSize,
                                                               @RequestParam(value = "creationDate[]",required = false) String[] creationDate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getBillingTypeGroupListByCriteria(creationDate, user.getSysClinicId(), null);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }







}
