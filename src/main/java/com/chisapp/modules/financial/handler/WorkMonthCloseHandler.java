package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.bean.WorkMonthClose;
import com.chisapp.modules.financial.service.WorkMonthCloseService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-25 13:37
 * @Version 1.0
 */
@RequestMapping("/workMonthClose")
@RestController
public class WorkMonthCloseHandler {
    @Autowired
    private WorkMonthCloseService workMonthCloseService;
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 定时创建
     * 每个月 25 号凌晨 0 点 5 分 0 秒 启动
     */
    @Scheduled(cron = "0 5 0 25 * *") // 秒 分 时 日 月 周
    private void autoSaveList () {
        workMonthCloseService.saveList();
    }

    /**
     * 月结操作
     * @param userDate
     * @return
     */
    @PutMapping("/updateToClose")
    public PageResult updateToClose(@RequestParam String userDate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        workMonthCloseService.updateToClose(DateUtils.parseToShort(userDate), user);
        return PageResult.success();
    }

    /**
     * 获取待处理的业务消息
     * @param beginDate
     * @param endDate
     * @return
     */
    @GetMapping("/getPendingMsgByDateAndSysClinicId")
    public PageResult getPendingMsgByDateAndSysClinicId(String beginDate, String endDate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> list = workMonthCloseService.getPendingMsgByDateAndSysClinicId(
                DateUtils.parseToShort(beginDate), DateUtils.parseToShort(endDate), user.getSysClinicId());
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取月结数据
     * @param userDate
     * @return
     */
    @GetMapping("/getClinicWorkMonthCloseData")
    public PageResult getClinicWorkMonthCloseData(@RequestParam String userDate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        WorkMonthClose workMonthClose = workMonthCloseService.getClinicWorkMonthCloseData(
                DateUtils.parseToShort(userDate), user.getSysClinicId());
        List<WorkMonthClose> list = new ArrayList<>();
        list.add(workMonthClose);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 检查是否有未月结的账期
     * @param year
     * @param month
     * @return
     */
    @GetMapping("/getUnClosedByYearAndMonth")
    public Boolean getUnClosedByYearAndMonth(@RequestParam Integer year, @RequestParam Integer month) {
        List<WorkMonthClose> workMonthCloseList = workMonthCloseService.getUnClosedByYearAndMonth(year, month);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        for (WorkMonthClose workMonthClose : workMonthCloseList) {
            if (workMonthClose.getSysClinicId().intValue() == user.getSysClinicId().intValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据条件进行查询
     * @param pageNum
     * @param pageSize
     * @param apYear
     * @param apMonth
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer apYear,
            @RequestParam(required = false) Integer apMonth){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = workMonthCloseService.getByCriteria(apYear, apMonth);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }





}
