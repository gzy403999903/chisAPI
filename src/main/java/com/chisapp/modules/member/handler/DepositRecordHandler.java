package com.chisapp.modules.member.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.member.service.DepositRecordService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/31 12:19
 * @Version 1.0
 */
@RequestMapping("/depositRecord")
@RestController
public class DepositRecordHandler {

    private DepositRecordService depositRecordService;
    @Autowired
    public void setDepositRecordService(DepositRecordService depositRecordService) {
        this.depositRecordService = depositRecordService;
    }

    /**
     * 保存一条储值记录
     * @param mrmMemberId
     * @param paymentRecordJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestParam Integer mrmMemberId, @RequestParam String paymentRecordJson) {
        depositRecordService.save(mrmMemberId, paymentRecordJson);
        return PageResult.success();
    }

    /**
     * 保存一条储值退回记录
     * @param lsh
     * @return
     */
    @PostMapping("/saveForReturned")
    public PageResult saveForReturned(@RequestParam String lsh) {
        depositRecordService.saveForReturned(lsh);
        return PageResult.success();
    }

    /**
     * 获取指定机构的储值记录
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param mrmMemberName
     * @param returned
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) Boolean returned){

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                depositRecordService.getByCriteria(user.getSysClinicId(), creationDate, lsh, mrmMemberName, returned);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取所有机构的储值记录
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param mrmMemberName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String mrmMemberName,
            @RequestParam(required = false) Boolean returned){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                depositRecordService.getByCriteria(null, creationDate, lsh, mrmMemberName, returned);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


}
