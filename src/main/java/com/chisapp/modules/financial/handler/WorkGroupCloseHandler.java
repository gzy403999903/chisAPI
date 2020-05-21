package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.DateUtils;
import com.chisapp.modules.financial.bean.WorkGroupClose;
import com.chisapp.modules.financial.service.WorkGroupCloseService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-05-16 17:01
 * @Version 1.0
 */
@RequestMapping("/workGroupClose")
@RestController
public class WorkGroupCloseHandler {
    @Autowired
    private WorkGroupCloseService workGroupCloseService;

    /* -------------------------------------------------------------------------------------------------------------- */

    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("workGroupClose", workGroupCloseService.getById(id));
        }
    }

    /**
     * 检查是否登记班次
     * @return
     */
    @GetMapping("/checkRegistration")
    public Boolean checkRegistration(@RequestParam String userDate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return workGroupCloseService.checkRegistration(DateUtils.parseToShort(userDate), user);
    }

    /**
     * 创建一个班次记录
     * @param userDate
     * @param flmWorkGroupId
     * @return
     */
    @PostMapping("/registration")
    public PageResult registration(@RequestParam String userDate, @RequestParam Integer flmWorkGroupId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        workGroupCloseService.registration(DateUtils.parseToShort(userDate), flmWorkGroupId, user);
        return PageResult.success();
    }

    /**
     * 班结操作
     * @param workGroupClose
     * @return
     */
    @PutMapping("/updateToClose")
    public PageResult updateToClose(WorkGroupClose workGroupClose) {
        workGroupClose.setEndDate(new Date());
        workGroupClose.setCloseState(true);
        workGroupCloseService.update(workGroupClose);
        return PageResult.success();
    }

    /**
     * 更新班结支付方式
     * @param workGroupClose
     * @return
     */
    @PutMapping("/updateToPayments")
    public PageResult updateToPayments(WorkGroupClose workGroupClose) {
        if (!workGroupClose.getCloseState()) {
            throw new RuntimeException("班结后才可进行该操作");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        workGroupClose.setLastUpdaterId(user.getId());
        workGroupClose.setLastUpdateDate(new Date());
        workGroupCloseService.update(workGroupClose);
        return PageResult.success();
    }

    /**
     * 根据查询条件获取个人的班结记录
     * @param pageNum
     * @param pageSize
     * @param logicDate
     * @param closeState
     * @return
     */
    @GetMapping("/getOperatorByCriteria")
    public PageResult getOperatorByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] logicDate, // 创建日期
            @RequestParam(required = false) Boolean closeState){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                workGroupCloseService.getByCriteria(logicDate, user.getId(), closeState, null, null);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据条件获取所有班结记录
     * @param pageNum
     * @param pageSize
     * @param logicDate
     * @param closeState
     * @param sysClinicName
     * @param operatorName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] logicDate, // 创建日期
            @RequestParam(required = false) Boolean closeState,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String operatorName){

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                workGroupCloseService.getByCriteria(logicDate, user.getId(), closeState, sysClinicName, operatorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


}
