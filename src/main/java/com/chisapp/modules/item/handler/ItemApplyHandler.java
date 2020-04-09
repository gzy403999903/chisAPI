package com.chisapp.modules.item.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.enums.ItemTypeEnum;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.item.bean.ItemApply;
import com.chisapp.modules.item.bean.ItemType;
import com.chisapp.modules.item.service.ItemApplyService;
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
@RequestMapping("/itemApply")
@RestController
public class ItemApplyHandler {

    private ItemApplyService itemApplyService;
    @Autowired
    public void setItemApplyService(ItemApplyService itemApplyService) {
        this.itemApplyService = itemApplyService;
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
            map.put("itemApply", itemApplyService.getById(id));
        }
    }

    /**
     * 保存医技项目操作
     * @param itemApply
     * @param result
     * @return
     */
    @PostMapping("/medicalItem/save")
    public PageResult medicalItemSave (@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApply.setCimItemTypeId(ItemTypeEnum.MEDICAL_ITEM.getIndex());
        itemApplyService.save(itemApply);
        return PageResult.success();
    }

    /**
     * 保存辅助项目操作
     * @param itemApply
     * @param result
     * @return
     */
    @PostMapping("/adjuvantItem/save")
    public PageResult adjuvantItemSave (@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApply.setCimItemTypeId(ItemTypeEnum.ADJUVANT_ITEM.getIndex());
        itemApplyService.save(itemApply);
        return PageResult.success();
    }

    /**
     * 保存其他项目操作
     * @param itemApply
     * @param result
     * @return
     */
    @PostMapping("/otherItem/save")
    public PageResult otherItemSave (@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApply.setCimItemTypeId(ItemTypeEnum.OTHER_ITEM.getIndex());
        itemApplyService.save(itemApply);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param itemApply
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.update(itemApply);
        return PageResult.success();
    }

    /**
     * 定价操作
     * @param itemApply
     * @return
     */
    @PutMapping("/pricing")
    public PageResult pricing(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.pricing(itemApply);
        return PageResult.success();
    }

    /**
     * 审核定价驳回操作
     * @param itemApply
     * @return
     */
    @PutMapping("/cancelPricing")
    public PageResult cancelPricing(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.cancelPricing(itemApply);
        return PageResult.success();
    }

    /**
     * 审核项目驳回操作
     * @param itemApply
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.unapproved(itemApply);
        return PageResult.success();
    }

    /**
     * 审核通过操作
     * @param itemApply
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.approved(itemApply);
        return PageResult.success();
    }

    /**
     * 审批定价驳回操作
     * @param itemApply
     * @return
     */
    @PutMapping("/lastCancelPricing")
    public PageResult lastCancelPricing(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.lastCancelPricing(itemApply);
        return PageResult.success();
    }

    /**
     * 审批项目驳回操作
     * @param itemApply
     * @return
     */
    @PutMapping("/lastUnapproved")
    public PageResult lastUnapproved(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.lastUnapproved(itemApply);
        return PageResult.success();
    }

    /**
     * 审批通过操作
     * @param itemApply
     * @return
     */
    @PutMapping("/lastApproved")
    public PageResult lastApproved(@Valid ItemApply itemApply, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        itemApplyService.lastApproved(itemApply);
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
        List<Map<String, Object>> pageList = itemApplyService.getByCriteria(creationDate, approveState, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }
}
