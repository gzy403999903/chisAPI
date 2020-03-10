package com.chisapp.modules.inventory.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.enums.ApproveStateEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.SelfUsedRecord;
import com.chisapp.modules.inventory.service.SelfUsedRecordService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/3 11:28
 * @Version 1.0
 */
@RequestMapping("/selfUsedRecord")
@RestController
public class SelfUsedRecordHandler {

    private SelfUsedRecordService selfUsedRecordService;
    @Autowired
    public void setSelfUsedRecordService(SelfUsedRecordService selfUsedRecordService) {
        this.selfUsedRecordService = selfUsedRecordService;
    }

    /**
     * 保存操作
     * @param mapJson
     * @return
     */
    @PostMapping("/saveList")
    public PageResult saveList(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("selfUsedRecordListJson") == null) {
            return PageResult.fail().msg("缺少请求参数");
        }
        String selfUsedRecordListJson = map.get("selfUsedRecordListJson");
        List<SelfUsedRecord> selfUsedRecordList = JSONUtils.parseJsonToObject(selfUsedRecordListJson, new TypeReference<List<SelfUsedRecord>>() {});
        selfUsedRecordService.saveList(selfUsedRecordList);
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(@RequestParam String lsh) {
        selfUsedRecordService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(@RequestParam String lsh) {
        selfUsedRecordService.approved(lsh);
        return PageResult.success();
    }

    /**
     * 获取领用明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param sysClinicName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String sysClinicName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                selfUsedRecordService.getByCriteria(null, creationDate, lsh, sysClinicName, ApproveStateEnum.APPROVED.getIndex());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取机构领用明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param approveState
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) Byte approveState){

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                selfUsedRecordService.getByCriteria(user.getSysClinicId(), creationDate, lsh, null, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


    /**
     * 获取机构领用汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param approveState
     * @return
     */
    @GetMapping("/getClinicGroupListByCriteria")
    public PageResult getClinicGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) Byte approveState){

        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                selfUsedRecordService.getClinicGroupListByCriteria(user.getSysClinicId(), creationDate, lsh, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据流水号获取领用明细
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh(@RequestParam String lsh) {
        List<Map<String, Object>> list = selfUsedRecordService.getByLsh(lsh);
        return PageResult.success().resultSet("list", list);
    }

}
