package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.service.WorkMonthCloseService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

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







}
