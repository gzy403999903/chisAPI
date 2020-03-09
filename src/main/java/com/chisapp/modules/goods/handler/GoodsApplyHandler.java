package com.chisapp.modules.goods.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.goods.bean.GoodsApply;
import com.chisapp.modules.goods.service.GoodsApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/8/7 16:25
 * @Version 1.0
 */
@RequestMapping("/goodsApply")
@RestController
public class GoodsApplyHandler {

    private GoodsApplyService goodsApplyService;
    @Autowired
    public void setGoodsApplyService(GoodsApplyService goodsApplyService) {
        this.goodsApplyService = goodsApplyService;
    }

    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("goodsApply", goodsApplyService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param goodsApply
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.save(goodsApply);
        return PageResult.success();
    }

    /**
     * 撤销操作
     * @param goodsApply
     * @return
     */
    @PutMapping("/cancel")
    public PageResult cancel(@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.cancel(goodsApply);
        return PageResult.success();
    }

    /**
     * 定价操作
     * @param goodsApply
     * @return
     */
    @PutMapping("/pricing")
    public PageResult pricing(@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.pricing(goodsApply);
        return PageResult.success();
    }

    /**
     * 定价驳回操作
     * @param goodsApply
     * @return
     */
    @PutMapping("/cancelPricing")
    public PageResult cancelPricing(@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.cancelPricing(goodsApply);
        return PageResult.success();
    }

    /**
     * 项目驳回操作
     * @param goodsApply
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.unapproved(goodsApply);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param goodsApply
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(@Valid GoodsApply goodsApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        goodsApplyService.approved(goodsApply);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param approveState
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Byte approveState, // 申请状态
            @RequestParam(required = false) String name){ // 项目名称或助记码

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = goodsApplyService.getByCriteria(creationDate, approveState, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }
}
