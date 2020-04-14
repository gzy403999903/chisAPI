package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.InventoryAddReportService;
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
@RequestMapping("/inventoryAddReport")
@RestController
public class InventoryAddReportHandler {

    private InventoryAddReportService inventoryAddReportService;
    @Autowired
    public void setInventoryAddReportService(InventoryAddReportService inventoryAddReportService) {
        this.inventoryAddReportService = inventoryAddReportService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取全机构 采购成本
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param pemSupplierName
     * @return
     */
    @GetMapping("/getPurchaseCostAmountByCriteria")
    public PageResult getPurchaseCostAmountByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String pemSupplierName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryAddReportService.getPurchaseCostAmountByCriteria(
                creationDate, null, sysClinicName, pemSupplierName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取本机构 采购成本
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param pemSupplierName
     * @return
     */
    @GetMapping("/getClinicPurchaseCostAmountByCriteria")
    public PageResult getClinicPurchaseCostAmountByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String pemSupplierName){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryAddReportService.getPurchaseCostAmountByCriteria(
                creationDate, user.getSysClinicId(), null, pemSupplierName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }


}
