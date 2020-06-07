package com.chisapp.modules.purchase.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.purchase.bean.AssessCost;
import com.chisapp.modules.purchase.service.AssessCostService;
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
 * @Date: 2020-06-04 16:19
 * @Version 1.0
 */
@RequestMapping("/assessCost")
@RestController
public class AssessCostHandler {

    @Autowired
    private AssessCostService assessCostService;
    /* -------------------------------------------------------------------------------------------------------------- */

    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("assessCost", assessCostService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param assessCost
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid AssessCost assessCost, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        assessCostService.save(assessCost);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param assessCost
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid AssessCost assessCost, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        assessCostService.update(assessCost);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Integer id) {
        assessCostService.delete(id);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param pemSupplierOid
     * @param pemSupplierName
     * @param gsmGoodsOid
     * @param gsmGoodsName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String pemSupplierOid,
            @RequestParam(required = false) String pemSupplierName,
            @RequestParam(required = false) String gsmGoodsOid,
            @RequestParam(required = false) String gsmGoodsName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                assessCostService.getByCriteria(pemSupplierOid, pemSupplierName, gsmGoodsOid, gsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }














}
