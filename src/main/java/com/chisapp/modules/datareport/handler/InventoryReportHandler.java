package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.InventoryReportService;
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
                inventoryReportService.getExpiryDateListByCriteria(
                        null, sysClinicName, (filterDays == null ? 120 : filterDays));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 全机构近效期库存表下载
     * @param response
     */
    @GetMapping("/downloadExpiryDateExcel")
    public void downloadExpiryDateExcel(HttpServletResponse response,
                                        @RequestParam(required = false) String sysClinicName,
                                        @RequestParam(required = false) Integer filterDays) {

        XSSFWorkbook workbook = this.inventoryReportService.downloadExpiryDateExcel(null, sysClinicName, filterDays);
        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today = dateFormat.format(new Date());
        String fileName = today +  "近效期库存表.xlsx";
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
                inventoryReportService.getExpiryDateListByCriteria(user.getSysClinicId(), null, filterDays);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 全机构近效期库存表下载
     * @param response
     */
    @GetMapping("/downloadClinicExpiryDateExcel")
    public void downloadClinicExpiryDateExcel(HttpServletResponse response,
                                              @RequestParam(required = false) Integer filterDays) {

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        XSSFWorkbook workbook = this.inventoryReportService.downloadExpiryDateExcel(
                user.getSysClinicId(), null, (filterDays == null ? 120 : filterDays));
        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today = dateFormat.format(new Date());
        String fileName = user.getClinic().getName() + "_" + today +  "近效期库存表.xlsx";
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
     * 获取全机构 库存动销分析
     * @param pageNum
     * @param pageSize
     * @param sysClinicName
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param days
     * @param sellFrequency
     * @return
     */
    @GetMapping("/getSellFrequencyListByCriteria")
    public PageResult getSellFrequencyListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                      @RequestParam(defaultValue="1") Integer pageSize,
                                                      @RequestParam(required = false) String sysClinicName,
                                                      @RequestParam(required = false) Integer quantity,
                                                      @RequestParam(required = false) Integer gsmGoodsTypeId,
                                                      @RequestParam(required = false) String gsmGoodsOid,
                                                      @RequestParam(required = false) String gsmGoodsName,
                                                      @RequestParam(required = false) Integer days,
                                                      @RequestParam(required = false) Integer sellFrequency,
                                                      @RequestParam(required = false) Integer sellQuantity,
                                                      @RequestParam(required = false) Integer minAge) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryReportService.getSellFrequencyListByCriteria(
                null, sysClinicName, quantity, gsmGoodsTypeId, gsmGoodsOid, gsmGoodsName, days,
                sellFrequency, sellQuantity, minAge);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 库存动销分析
     * @param pageNum
     * @param pageSize
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @param days
     * @param sellFrequency
     * @return
     */
    @GetMapping("/getClinicSellFrequencyListByCriteria")
    public PageResult getClinicSellFrequencyListByCriteria (@RequestParam(defaultValue="1") Integer pageNum,
                                                            @RequestParam(defaultValue="1") Integer pageSize,
                                                            @RequestParam(required = false) Integer quantity,
                                                            @RequestParam(required = false) Integer gsmGoodsTypeId,
                                                            @RequestParam(required = false) String gsmGoodsOid,
                                                            @RequestParam(required = false) String gsmGoodsName,
                                                            @RequestParam(required = false) Integer days,
                                                            @RequestParam(required = false) Integer sellFrequency,
                                                            @RequestParam(required = false) Integer sellQuantity,
                                                            @RequestParam(required = false) Integer minAge) {

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryReportService.getSellFrequencyListByCriteria(
                user.getSysClinicId(), null, quantity, gsmGoodsTypeId, gsmGoodsOid, gsmGoodsName,
                days, sellFrequency, sellQuantity, minAge);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }







}
