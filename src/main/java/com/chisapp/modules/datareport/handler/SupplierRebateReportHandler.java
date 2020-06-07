package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.SupplierRebateReportService;
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
 * @Date: 2020-06-05 13:56
 * @Version 1.0
 */
@RequestMapping("/supplierRebateReport")
@RestController
public class SupplierRebateReportHandler {

    @Autowired
    private SupplierRebateReportService supplierRebateReportService;

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取对应的库存返利明细
     * @param pageNum
     * @param pageSize
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @return
     */
    @GetMapping("/getInventoryRebateByCriteria")
    public PageResult getInventoryRebateByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String gsmGoodsOid,
            @RequestParam(required = false) String gsmGoodsName,
            @RequestParam(required = false) String pemSupplierOid,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(defaultValue = "clinic") String groupBy){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = supplierRebateReportService.getInventoryRebateByCriteria(
                gsmGoodsOid, gsmGoodsName, pemSupplierOid, pemSupplierName, sysClinicName, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取对应的销售返利明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param sysClinicName
     * @param groupBy
     * @return
     */
    @GetMapping("/getSellRebateByCriteria")
    public PageResult getSellRebateByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String gsmGoodsOid,
            @RequestParam(required = false) String gsmGoodsName,
            @RequestParam(required = false) String pemSupplierOid,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(defaultValue = "clinic") String groupBy){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = supplierRebateReportService.getSellRebateByCriteria(
                creationDate, gsmGoodsOid, gsmGoodsName, pemSupplierOid, pemSupplierName, sysClinicName, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }



}
