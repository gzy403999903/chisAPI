package com.chisapp.modules.inventory.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.inventory.bean.InventoryAllot;
import com.chisapp.modules.inventory.service.InventoryAllotService;
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
 * @Date: 2019/10/15 18:09
 * @Version 1.0
 */
@RequestMapping("/inventoryAllot")
@RestController
public class InventoryAllotHandler {

    private InventoryAllotService inventoryAllotService;
    @Autowired
    public void setInventoryAllotService(InventoryAllotService inventoryAllotService) {
        this.inventoryAllotService = inventoryAllotService;
    }

    /**
     * 保存操作
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("inventoryAllotJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        String inventoryAllotJson = map.get("inventoryAllotJson");
        List<InventoryAllot> allotList =
                JSONUtils.parseJsonToObject(inventoryAllotJson, new TypeReference<List<InventoryAllot>>() {});
        inventoryAllotService.save(allotList);
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(String lsh) {
        inventoryAllotService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(String lsh) {
        inventoryAllotService.approved(lsh);
        return PageResult.success();
    }

    /**
     * 查询机构库房调拨明细
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
                inventoryAllotService.getClinicListByCriteria(creationDate, user.getSysClinicId(), approveState, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 查询机构库房调拨汇总
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
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) Byte approveState){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryAllotService.getClinicLshGroupListByCriteria(creationDate, user.getSysClinicId(), approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


    /**
     * 获取流水号对应明细
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public List<Map<String, Object>> getByLsh(String lsh) {
        return inventoryAllotService.getByLsh(lsh);
    }








}
