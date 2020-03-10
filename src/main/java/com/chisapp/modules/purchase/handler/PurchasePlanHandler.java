package com.chisapp.modules.purchase.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.purchase.bean.PurchasePlan;
import com.chisapp.modules.purchase.service.PurchasePlanService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/28 14:09
 * @Version 1.0
 */
@RequestMapping("/purchasePlan")
@RestController
public class PurchasePlanHandler {

    private PurchasePlanService purchasePlanService;
    @Autowired
    public void setPurchasePlanService(PurchasePlanService purchasePlanService) {
        this.purchasePlanService = purchasePlanService;
    }

    /**
     * 保存采购计划
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("planJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        List<PurchasePlan> list =
                JSONUtils.parseJsonToObject(map.get("planJson"),  new TypeReference<List<PurchasePlan>>() {});
        purchasePlanService.save(list);
        return PageResult.success();
    }

    /**
     * 撤销计划
     * @param planIdArray
     * @return
     */
    @PutMapping("/cancel")
    public PageResult cancel(@RequestParam("planIdArray[]") Integer[] planIdArray) {
        purchasePlanService.cancel(purchasePlanService.getByPlanIdList(Arrays.asList(planIdArray)));
        return PageResult.success();
    }

    /**
     * 驳回计划
     * @param planIdArray
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(@RequestParam("planIdArray[]") Integer[] planIdArray) {
        purchasePlanService.unapproved(purchasePlanService.getByPlanIdList(Arrays.asList(planIdArray)));
        return PageResult.success();
    }

    /**
     * 待采购计划
     * @param planIdArray
     * @return
     */
    @PutMapping("/purchasing")
    public PageResult purchasing(@RequestParam("planIdArray[]") Integer[] planIdArray) {
        purchasePlanService.purchasing(purchasePlanService.getByPlanIdList(Arrays.asList(planIdArray)));
        return PageResult.success();
    }

    /**
     * 获取机构请货计划明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @param gsmGoodsName
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String gsmGoodsName){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                purchasePlanService.getClinicListByCriteria(creationDate, user.getSysClinicId(), approveState, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全部机构请货计划明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @param sysClinicName
     * @param gsmGoodsName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String gsmGoodsName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                purchasePlanService.getByCriteria(creationDate, approveState, sysClinicName, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取待审核的汇总采购计划
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param gsmGoodsName
     * @return
     */
    @GetMapping("/getPendingGroupListByCriteria")
    public PageResult getPendingGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String gsmGoodsName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = purchasePlanService.getPendingGroupListByCriteria(creationDate, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取待采购的汇总采购计划
     * @return
     */
    @GetMapping("/getPurchasingGroupListByCriteria")
    public PageResult getPurchasingGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "approveDate[]",required = false) String[] approveDate){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =  purchasePlanService.getPurchasingGroupListByCriteria(approveDate);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据采购计划ID 获取对应的机构汇总采购计划
     * @param planIdArray
     * @return
     */
    @GetMapping("/getClinicGroupListByPlanIdArray")
    public PageResult getClinicGroupListByPlanIdArray (@RequestParam("planIdArray[]") Integer[] planIdArray){

        List<Map<String, Object>> list = purchasePlanService.getClinicGroupListByPlanIdList(Arrays.asList(planIdArray));
        return PageResult.success().resultSet("groupList", list);
    }

}
