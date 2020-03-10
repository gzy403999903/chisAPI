package com.chisapp.modules.inventory.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.inventory.service.InventoryService;
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
 * @Date: 2019/9/30 15:53
 * @Version 1.0
 */
@RequestMapping("/inventory")
@RestController
public class InventoryHandler {

    private InventoryService inventoryService;
    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * 拆零操作
     * @return
     */
    @PutMapping("/splitQuantity")
    public PageResult splitQuantity(@RequestParam Integer id) {
        inventoryService.splitQuantity(id);
        return PageResult.success();
    }

    /**
     * 获取机构对应库房的 pch 库存
     * (检索库存)
     * @return
     */
    @GetMapping("/getClinicPchEnabledLikeByCriteria")
    public List<Map<String, Object>> getClinicPchEnabledLikeByCriteria(@RequestParam Integer iymInventoryTypeId,
                                                                       @RequestParam String gsmGoodsName) {
        if (gsmGoodsName == null || gsmGoodsName.trim().equals("")) {
            return null;
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return inventoryService.getClinicPchEnabledLikeByCriteria(user.getSysClinicId(), iymInventoryTypeId, gsmGoodsName);
    }

    /**
     * 获取机构对应库房的 pch 库存
     * (出库发药)
     * @return
     */
    @GetMapping("/getClinicPchEnabledByCriteriaForOutInventory")
    public PageResult getClinicPchEnabledByCriteriaForOutInventory(@RequestParam Integer gsmGoodsId) {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Map<String, Object>> list =
                inventoryService.getClinicPchEnabledByCriteriaForOutInventory(user.getSysClinicId(), gsmGoodsId);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 获取机构退货 pch 库存
     * (采购退货)
     * @return
     */
    @GetMapping("/getClinicSubtractPchLikeByCriteria")
    public List<Map<String, Object>> getClinicSubtractPchLikeByCriteria(@RequestParam Integer iymInventoryTypeId,
                                                                        @RequestParam Integer pemSupplierId,
                                                                        @RequestParam String gsmGoodsName) {
        if (gsmGoodsName == null || gsmGoodsName.trim().equals("")) {
            return null;
        }

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return inventoryService.getClinicSubtractPchLikeByCriteria(user.getSysClinicId(), iymInventoryTypeId, pemSupplierId, gsmGoodsName);
    }


    /**
     * 获取本机构 ph 库存
     * @param pageNum
     * @param pageSize
     * @param iymInventoryTypeId
     * @param showZero
     * @param gsmGoodsName
     * @param ph
     * @return
     */
    @GetMapping("/getClinicPhGroupListByCriteria")
    public PageResult getClinicPhGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer iymInventoryTypeId,
            @RequestParam(defaultValue = "false") Boolean showZero,
            @RequestParam(required = false) String gsmGoodsName,
            @RequestParam(required = false) String ph){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
               inventoryService.getClinicPhGroupListByCriteria(user.getSysClinicId(), iymInventoryTypeId, showZero, gsmGoodsName, ph);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 ph 库存
     * @param pageNum
     * @param pageSize
     * @param showZero
     * @param sysClinicName
     * @param gsmGoodsName
     * @param ph
     * @return
     */
    @GetMapping("/getPhGroupListByCriteria")
    public PageResult getPhGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(defaultValue = "false") Boolean showZero,
            @RequestParam(required = false) String sysClinicName,
            @RequestParam(required = false) String gsmGoodsName,
            @RequestParam(required = false) String ph){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryService.getPhGroupListByCriteria(showZero, sysClinicName, gsmGoodsName, ph);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取本机构 pch 库存
     * @param pageNum
     * @param pageSize
     * @param iymInventoryTypeId
     * @param showZero
     * @param gsmGoodsName
     * @param ph
     * @return
     */
    @GetMapping("/getClinicPchListByCriteria")
    public PageResult getClinicPchListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer iymInventoryTypeId,
            @RequestParam(defaultValue = "false") Boolean showZero,
            @RequestParam(required = false) String gsmGoodsName,
            @RequestParam(required = false) String ph){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                inventoryService.getClinicPchListByCriteria(user.getSysClinicId(), iymInventoryTypeId, showZero, gsmGoodsName, ph);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


}
