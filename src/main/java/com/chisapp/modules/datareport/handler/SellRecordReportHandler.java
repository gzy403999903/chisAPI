package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.SellRecordReportService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * 获取所有机构 销售明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param lsh
     * @param entityName
     * @param mrmMemberName
     * @param sellerName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(value = "invoiceDate[]",required = false) String[] invoiceDate,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) Integer sysSellTypeId,
            @RequestParam(required = false) Integer entityTypeId,
            @RequestParam(required = false) String entityOid,
            @RequestParam(required = false) String entityName,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer sellerId,
            @RequestParam(required = false) String sellerName,
            @RequestParam(required = false) String pemSupplierOid,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(value = "sellClassifyId[]", required = false) Integer[] sellClassifyId){

        Map<String, Object> countMap = sellRecordReportService.countSellRecordByCriteria(
                creationDate, invoiceDate, null,sysClinicName, lsh, sysSellTypeId, entityTypeId, entityOid,
                entityName, mrmMemberName, phone, sellerId, sellerName, pemSupplierOid, pemSupplierName, sellClassifyId);

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getByCriteria(creationDate, invoiceDate, null,
                sysClinicName, lsh, sysSellTypeId, entityTypeId, entityOid, entityName, mrmMemberName, phone, sellerId,
                sellerName, pemSupplierOid, pemSupplierName, sellClassifyId);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo).resultSet("countMap", countMap);
    }

    /**
     * 获取当前机构 销售明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param entityName
     * @param mrmMemberName
     * @param sellerName
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(value = "invoiceDate[]",required = false) String[] invoiceDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) Integer sysSellTypeId,
            @RequestParam(required = false) Integer entityTypeId,
            @RequestParam(required = false) String entityOid,
            @RequestParam(required = false) String entityName,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer sellerId,
            @RequestParam(required = false) String sellerName,
            @RequestParam(required = false) String pemSupplierOid,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(value = "sellClassifyId[]", required = false) Integer[] sellClassifyId){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Map<String, Object> countMap = sellRecordReportService.countSellRecordByCriteria(
                creationDate, invoiceDate, user.getSysClinicId(), null, lsh, sysSellTypeId, entityTypeId,
                entityOid, entityName, mrmMemberName, phone, sellerId, sellerName, pemSupplierOid, pemSupplierName, sellClassifyId);

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getByCriteria(creationDate, invoiceDate, user.getSysClinicId(),
                null, lsh, sysSellTypeId, entityTypeId, entityOid, entityName, mrmMemberName, phone, sellerId,
                sellerName, pemSupplierOid, pemSupplierName, sellClassifyId);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo).resultSet("countMap", countMap);
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
    public PageResult getBillingTypeGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "sysClinicId") String groupBy,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sysClinicName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getBillingTypeGroupListByCriteria(creationDate, null, sysClinicName, groupBy);
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
    public PageResult getClinicBillingTypeGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "sysClinicId") String groupBy,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getBillingTypeGroupListByCriteria(creationDate, user.getSysClinicId(), null, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 销售毛利(按流水号)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param sysClinicName
     * @param goodsMarginRate
     * @param marginRate
     * @param goodsDiscountRate
     * @param itemDiscountRate
     * @param discountRate
     * @return
     */
    @GetMapping("/getLshMarginRateListByCriteria")
    public PageResult getLshMarginRateListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                     @RequestParam(defaultValue="1") Integer pageSize,
                                                     @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                     @RequestParam(required = false) String lsh,
                                                     @RequestParam(required = false) String sysClinicName,
                                                     @RequestParam(required = false) String goodsMarginRate,
                                                     @RequestParam(required = false) String marginRate,
                                                     @RequestParam(required = false) Integer goodsDiscountRate,
                                                     @RequestParam(required = false) Integer itemDiscountRate,
                                                     @RequestParam(required = false) Integer discountRate) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getMarginRateListByCriteria(
                        creationDate, null, lsh, sysClinicName, goodsMarginRate, marginRate,
                        goodsDiscountRate, itemDiscountRate, discountRate, "lsh");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 销售毛利(按流水号)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param goodsMarginRate
     * @param marginRate
     * @param goodsDiscountRate
     * @param itemDiscountRate
     * @param discountRate
     * @return
     */
    @GetMapping("/getClinicLshMarginRateListByCriteria")
    public PageResult getClinicLshMarginRateListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                           @RequestParam(defaultValue="1") Integer pageSize,
                                                           @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                           @RequestParam(required = false) String lsh,
                                                           @RequestParam(required = false) String goodsMarginRate,
                                                           @RequestParam(required = false) String marginRate,
                                                           @RequestParam(required = false) Integer goodsDiscountRate,
                                                           @RequestParam(required = false) Integer itemDiscountRate,
                                                           @RequestParam(required = false) Integer discountRate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getMarginRateListByCriteria(
                        creationDate, user.getSysClinicId(), lsh, null, goodsMarginRate, marginRate,
                        goodsDiscountRate, itemDiscountRate, discountRate, "lsh");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 日销售报表
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getSellRecordDailyByCreationDate")
    public PageResult getSellRecordDailyByCreationDate(
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]") String[] creationDate,
            Integer queryMonth) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getSellRecordDailyByCreationDate(creationDate, queryMonth);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 导出日销售报表
     * @param response
     */
    @GetMapping("/downloadSellRecordDailyExcel")
    public void downloadSellRecordDailyExcel(HttpServletResponse response,
                                             @RequestParam(value = "creationDate[]") String[] creationDate,
                                             Integer queryMonth) {

        XSSFWorkbook workbook = sellRecordReportService.downloadDaySellRecordExcel(creationDate, queryMonth);
        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        // DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String fileName = "日销售报表.xlsx";
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全机构 销售排行
     * @param pageNum
     * @param pageSize
     * @param groupBy
     * @param creationDate
     * @param sysClinicName
     * @param sysSellTypeId
     * @param entityTypeId
     * @param entityOid
     * @param entityName
     * @return
     */
    @GetMapping("/getSellRecordSortByCriteria")
    public PageResult getSellRecordSortByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "sysClinicId") String groupBy,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) Integer sysSellTypeId,
            @RequestParam(required = false) Integer entityTypeId,
            @RequestParam(required = false) String entityOid,
            @RequestParam(required = false) String entityName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getSellRecordSortByCriteria(
                creationDate, null, sysClinicName, sysSellTypeId, entityTypeId, entityOid, entityName, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 销售排行
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysSellTypeId
     * @param entityTypeId
     * @param entityOid
     * @param entityName
     * @return
     */
    @GetMapping("/getClinicSellRecordSortByCriteria")
    public PageResult getClinicSellRecordSortByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "sysClinicId") String groupBy,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) Integer sysSellTypeId,
            @RequestParam(required = false) Integer entityTypeId,
            @RequestParam(required = false) String entityOid,
            @RequestParam(required = false) String entityName){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getSellRecordSortByCriteria(
                creationDate, user.getSysClinicId(), null, sysSellTypeId, entityTypeId, entityOid, entityName, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 销售统计财报
     * @param creationDate
     * @return
     */
    @GetMapping("/getSellRecordStatisticsByCriteria")
    public PageResult getSellRecordStatisticsByCriteria (
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate){

        List<Map<String, Object>> list = sellRecordReportService.getSellRecordStatisticsByCriteria(creationDate, null);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取本机构 销售统计财报
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicSellRecordStatisticsByCriteria")
    public PageResult getClinicSellRecordStatisticsByCriteria (
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> list = sellRecordReportService.getSellRecordStatisticsByCriteria(creationDate, user.getSysClinicId());
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 全机构 销售提成汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param sellerName
     * @return
     */
    @GetMapping("/getSellRecordCommissionByCriteria")
    public PageResult getSellRecordCommissionByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String sellerName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getSellRecordCommissionByCriteria(creationDate, null, sysClinicName, sellerName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 全机构 导出销售汇总报表
     * @param response
     * @param creationDate
     * @param sysClinicName
     * @param sellerName
     */
    @GetMapping("/downloadSellRecordCommissionExcel")
    public void downloadSellRecordCommissionExcel(
            HttpServletResponse response,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String sellerName) {

        XSSFWorkbook workbook = sellRecordReportService.downloadSellRecordCommissionExcel(
                creationDate,null, sysClinicName, sellerName);
        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        String fileName = "销售汇总(按人员).xlsx";
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本机构 销售提成汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sellerName
     * @return
     */
    @GetMapping("/getClinicSellRecordCommissionByCriteria")
    public PageResult getClinicSellRecordCommissionByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sellerName){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                sellRecordReportService.getSellRecordCommissionByCriteria(creationDate, user.getSysClinicId(), null, sellerName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 本机构 导出销售汇总报表
     * @param response
     * @param creationDate
     * @param sellerName
     */
    @GetMapping("/downloadClinicSellRecordCommissionExcel")
    public void downloadClinicSellRecordCommissionExcel(
            HttpServletResponse response,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String sellerName) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        XSSFWorkbook workbook = sellRecordReportService.downloadSellRecordCommissionExcel(
                creationDate,user.getSysClinicId(), null, sellerName);
        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        String fileName = "销售汇总(按人员).xlsx";
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 销售协定成本
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
    @GetMapping("/getSellAssessCostByCriteria")
    public PageResult getSellAssessCostByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                   @RequestParam(defaultValue="1") Integer pageSize,
                                                   @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                   @RequestParam(required = false) String gsmGoodsOid,
                                                   @RequestParam(required = false) String gsmGoodsName,
                                                   @RequestParam(required = false) String pemSupplierOid,
                                                   @RequestParam(required = false) String pemSupplierName,
                                                   @RequestParam(required = false) String sysClinicName,
                                                   @RequestParam(defaultValue="clinic") String groupBy) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = sellRecordReportService.getSellAssessCostByCriteria(
                creationDate, gsmGoodsOid, gsmGoodsName, pemSupplierOid, pemSupplierName, sysClinicName, groupBy);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }








}
