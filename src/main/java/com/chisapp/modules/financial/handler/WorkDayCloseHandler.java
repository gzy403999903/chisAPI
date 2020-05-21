package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.bean.WorkDayClose;
import com.chisapp.modules.financial.service.WorkDayCloseService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-19 18:18
 * @Version 1.0
 */
@RequestMapping("/workDayClose")
@RestController
public class WorkDayCloseHandler {
    @Autowired
    private WorkDayCloseService workDayCloseService;

    /* -------------------------------------------------------------------------------------------------------------- */
    /**
     * 定时创建
     * 每天凌晨 0 点 1 分 0 秒 启动
     */
    @Scheduled(cron = "0 1 0 * * *") // 秒 分 时 日 月 周
    private void autoSaveList () {
        workDayCloseService.autoSaveList();
    }

    /**
     * 日结操作
     * @param userDate
     * @return
     */
    @PutMapping("/updateToClose")
    public PageResult updateToClose(String userDate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        workDayCloseService.updateToClose(DateUtils.parseToShort(userDate), user);
        return PageResult.success();
    }

    /**
     * 根据查询条件获取当前机构的日结记录
     * @param pageNum
     * @param pageSize
     * @param logicDate
     * @param closeState
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] logicDate, // 创建日期
            @RequestParam(required = false) Boolean closeState){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                workDayCloseService.getByCriteria(logicDate, user.getSysClinicId(), closeState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

}
