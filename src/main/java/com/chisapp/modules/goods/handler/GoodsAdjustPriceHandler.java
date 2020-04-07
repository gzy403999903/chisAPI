package com.chisapp.modules.goods.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.goods.bean.GoodsAdjustPrice;
import com.chisapp.modules.goods.service.GoodsAdjustPriceService;
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
@RequestMapping("/goodsAdjustPrice")
@RestController
public class GoodsAdjustPriceHandler {

    private GoodsAdjustPriceService goodsAdjustPriceService;
    @Autowired
    public void setGoodsAdjustPriceService(GoodsAdjustPriceService goodsAdjustPriceService) {
        this.goodsAdjustPriceService = goodsAdjustPriceService;
    }

    /**
     * 保存操作
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("gapJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        List<GoodsAdjustPrice> list =
                JSONUtils.parseJsonToObject(map.get("gapJson"),  new TypeReference<List<GoodsAdjustPrice>>() {});
        goodsAdjustPriceService.save(list);
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param lsh
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved (String lsh) {
        goodsAdjustPriceService.unapproved(lsh);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param lsh
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved (String lsh) {
        goodsAdjustPriceService.approved(lsh);
        return PageResult.success();
    }

    /**
     * 根据 lsh 获取对象集合
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh (@RequestParam("lsh") String lsh) {
        List<Map<String, Object>> list = goodsAdjustPriceService.getByLsh(lsh);
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
            @RequestParam(required = false) String name, // 商品名称
            @RequestParam(required = false) Byte approveState){ // 申请状态

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = goodsAdjustPriceService.getByCriteria(creationDate, name, approveState);
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
            @RequestParam(required = false) String lsh, // 商品名称
            @RequestParam(required = false) Byte approveState){ // 申请状态

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = goodsAdjustPriceService.getGroupListByCriteria(creationDate, lsh, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }


}
