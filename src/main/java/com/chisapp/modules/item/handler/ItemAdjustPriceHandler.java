package com.chisapp.modules.item.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.item.bean.ItemAdjustPrice;
import com.chisapp.modules.item.service.ItemAdjustPriceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/9 8:41
 * @Version 1.0
 */
@RequestMapping("/itemAdjustPrice")
@RestController
public class ItemAdjustPriceHandler {

    private ItemAdjustPriceService itemAdjustPriceService;
    @Autowired
    public void setItemAdjustPriceService(ItemAdjustPriceService itemAdjustPriceService) {
        this.itemAdjustPriceService = itemAdjustPriceService;
    }

    /**
     * 保存操作
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("iapJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        List<ItemAdjustPrice> list =
                JSONUtils.parseJsonToObject(map.get("iapJson"),  new TypeReference<List<ItemAdjustPrice>>() {});
        itemAdjustPriceService.save(list);
        return PageResult.success();
    }

    /**
     * 撤销操作
     * @param lsh
     * @return
     */
    @PutMapping("/cancel")
    public PageResult cancel (String lsh) {
        itemAdjustPriceService.cancel(lsh);
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved (String lsh) {
        itemAdjustPriceService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved (String lsh) {
        itemAdjustPriceService.approved(lsh);
        return PageResult.success();
    }

    /**
     * 根据 lsh 获取对象集合
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh (@RequestParam("lsh") String lsh) {
        List<Map<String, Object>> list = itemAdjustPriceService.getByLsh(lsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据查询条件获取调价明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param name
     * @param approveState
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) String name, // 项目名称
            @RequestParam(required = false) Byte approveState){ // 审批状态

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = itemAdjustPriceService.getByCriteria(creationDate, name, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据查询条件获取调价汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param approveState
     * @return
     */
    @GetMapping("/getGroupListByCriteria")
    public PageResult getGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) Byte approveState){ // 审批状态

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = itemAdjustPriceService.getGroupListByCriteria(creationDate, lsh, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }
}
