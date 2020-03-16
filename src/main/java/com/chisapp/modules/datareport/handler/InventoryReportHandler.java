package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.InventoryReportService;
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
 * @Date: 2020-03-16 10:37
 * @Version 1.0
 */
@RequestMapping("/inventoryReport")
@RestController
public class InventoryReportHandler {

    private InventoryReportService inventoryReportService;
    @Autowired
    public void setInventoryReportService(InventoryReportService inventoryReportService) {
        this.inventoryReportService = inventoryReportService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取全机构 近效期库存
     * @param pageNum
     * @param pageSize
     * @param sysClinicName
     * @param filterDays
     * @return
     */
    @GetMapping("/getExpiryDateListByCriteria")
    public PageResult getExpiryDateListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                   @RequestParam(defaultValue="1") Integer pageSize,
                                                   @RequestParam(required = false) String sysClinicName,
                                                   @RequestParam(required = false) Integer filterDays) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryReportService.getExpiryDateListByCriteria(null, sysClinicName, (filterDays == null ? 120 : filterDays));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 近效期库存
     * @param pageNum
     * @param pageSize
     * @param filterDays
     * @return
     */
    @GetMapping("/getClinicExpiryDateListByCriteria")
    public PageResult getClinicExpiryDateListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                         @RequestParam(defaultValue="1") Integer pageSize,
                                                         @RequestParam(required = false) Integer filterDays) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryReportService.getExpiryDateListByCriteria(user.getSysClinicId(), null, (filterDays == null ? 120 : filterDays));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }






















}
