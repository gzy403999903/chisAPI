package com.chisapp.modules.inventory.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.enums.ActionTypeEnum;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.InventoryAdd;
import com.chisapp.modules.inventory.service.InventoryAddService;
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
 * @Date: 2019/9/22 22:37
 * @Version 1.0
 */
@RequestMapping("/inventoryAdd")
@RestController
public class InventoryAddHandler {

    private InventoryAddService inventoryAddService;
    @Autowired
    public void setInventoryAddService(InventoryAddService inventoryAddService) {
        this.inventoryAddService = inventoryAddService;
    }

    /**
     * 计划入库
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("inventoryAddJson") == null || map.get("orderLsh") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        String inventoryAddJson = map.get("inventoryAddJson");
        String orderLsh = map.get("orderLsh");

        List<InventoryAdd> inventoryAddList =
                JSONUtils.parseJsonToObject(inventoryAddJson, new TypeReference<List<InventoryAdd>>() {});
        inventoryAddService.save(inventoryAddList, orderLsh, ActionTypeEnum.PURCHASE_PLAN_ADD.getIndex());
        return PageResult.success();
    }

    /**
     * 自采入库
     * @param mapJson
     * @return
     */
    @PostMapping("/saveForAlone")
    public PageResult saveForAlone (@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, Object>>() {});
        String inventoryAddJson;
        try {
            inventoryAddJson = map.get("inventoryAddJson").toString();
        } catch (NullPointerException e) {
            throw new RuntimeException("缺少请求参数");
        }

        List<InventoryAdd> inventoryAddList =
                JSONUtils.parseJsonToObject(inventoryAddJson, new TypeReference<List<InventoryAdd>>() {});
        inventoryAddService.save(inventoryAddList, null, ActionTypeEnum.PURCHASE_ALONE_ADD.getIndex());
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(String lsh) {
        inventoryAddService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(String lsh) {
        inventoryAddService.approved(lsh);
        return PageResult.success();
    }

    /**
     * 打印采购入库单 (仅做权限判断使用)
     * @return
     */
    @GetMapping("/printPurchaseAddBill")
    public PageResult printPurchaseAddBill () {
        return PageResult.success();
    }

    /**
     * 根据查询条件获取机构入库记录明细
     * [计划入库调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(required = false) String gsmGoodsOid,
            @RequestParam(required = false) String gsmGoodsName){

        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryAddService.getClinicListByCriteria(
                creationDate, user.getSysClinicId(), approveState, ActionTypeEnum.PURCHASE_PLAN_ADD.getIndex(),
                pemSupplierName, gsmGoodsOid, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据查询条件获取机构入库记录明细
     * [自采入库调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @return
     */
    @GetMapping("/getClinicListByCriteriaForAlone")
    public PageResult getClinicListByCriteriaForAlone (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(required = false) String gsmGoodsOid,
            @RequestParam(required = false) String gsmGoodsName){

        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryAddService.getClinicListByCriteria(
                creationDate, user.getSysClinicId(), approveState, ActionTypeEnum.PURCHASE_ALONE_ADD.getIndex(),
                pemSupplierName, gsmGoodsOid, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据查询条件获取机构入库汇总记录
     * [计划入库调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @return
     */
    @GetMapping("/getClinicLshGroupListByCriteria")
    public PageResult getClinicLshGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String pemSupplierName){

        // 获取创建人信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = inventoryAddService.getClinicLshGroupListByCriteria(
                creationDate, user.getSysClinicId(), approveState, pemSupplierName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据流水号获取对应对象集合
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh(String lsh) {
        return PageResult.success().resultSet("list", inventoryAddService.getByLsh(lsh));
    }



}
