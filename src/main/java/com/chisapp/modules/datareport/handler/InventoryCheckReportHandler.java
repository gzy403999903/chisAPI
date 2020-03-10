package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.InventoryCheckReportService;
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
 * @Date: 2020/1/8 23:08
 * @Version 1.0
 */
@RequestMapping("/inventoryCheckReport")
@RestController
public class InventoryCheckReportHandler {
    private InventoryCheckReportService inventoryCheckReportService;
    @Autowired
    public void setInventoryCheckReportService(InventoryCheckReportService inventoryCheckReportService) {
        this.inventoryCheckReportService = inventoryCheckReportService;
    }

    /**
     * 机构盘点表 --- 实盘
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getClinicByCriteriaForCurrent")
    public PageResult getClinicByCriteriaForCurrent (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam String checkWay,
            @RequestParam(required = false) Integer gsmGoodsTypeId){

        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryCheckReportService.getClinicByCriteriaForCurrent(user.getSysClinicId(), checkWay, gsmGoodsTypeId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 机构盘点表 --- 动盘
     * @param pageNum
     * @param pageSize
     * @param checkWay
     * @param gsmGoodsTypeId
     * @param checkDate
     * @return
     */
    @GetMapping("/getClinicByCriteriaForChanged")
    public PageResult getClinicByCriteriaForChanged (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam String checkWay,
            @RequestParam(required = false) Integer gsmGoodsTypeId,
            @RequestParam(value = "checkDate[]") String[] checkDate) {

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 执行实盘存储过程
        this.inventoryCheckReportService.executePcdInventoryCheckChanged(checkDate, user.getSysClinicId());
        // 获取实盘结果
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryCheckReportService.getClinicByCriteriaForChanged(user.getSysClinicId(), checkWay, gsmGoodsTypeId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        // 释放实盘存储过程资源
        this.inventoryCheckReportService.dropPcdInventoryCheckChanged();
        // 返回结果
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取下载盘点表权限
     * @return
     */
    @GetMapping("/downloadExcelAuthc")
    public PageResult downloadExcelAuthc () {
        return PageResult.success();
    }

    /**
     * 下载盘点表
     * @param response
     * @param checkType
     * @param checkWay
     * @param gsmGoodsTypeId
     * @param checkDate
     */
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response,
                              @RequestParam String checkType,
                              @RequestParam String checkWay,
                              @RequestParam(required = false) Integer gsmGoodsTypeId,
                              @RequestParam(value = "checkDate[]", required = false) String[] checkDate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        XSSFWorkbook workbook = null;

        if (checkType.equals("current")) {
            workbook = inventoryCheckReportService.downloadExcelForCurrent(user.getSysClinicId(), checkWay, gsmGoodsTypeId);
        }
        if (checkType.equals("changed")) {
            workbook = inventoryCheckReportService.downloadExcelForChanged(user.getSysClinicId(), checkWay, gsmGoodsTypeId, checkDate);
        }

        // 如果为 null 则不继续执行
        if (workbook == null) {
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today = dateFormat.format(new Date());
        String fileName = user.getClinic().getName() + "_" + today +  "盘点表.xlsx";
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









}
