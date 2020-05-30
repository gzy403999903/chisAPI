package com.chisapp.modules.goods.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.goods.bean.Equivalent;
import com.chisapp.modules.goods.service.EquivalentService;
import com.fasterxml.jackson.core.type.TypeReference;
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
 * @Date: 2020-05-29 15:44
 * @Version 1.0
 */
@RequestMapping("/equivalent")
@RestController
public class EquivalentHandler {
    @Autowired
    private EquivalentService equivalentService;

    /* -------------------------------------------------------------------------------------------------------------- */
    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("equivalent", equivalentService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param listJson
     * @return
     */
    @PostMapping("/saveList")
    public PageResult saveList (@RequestBody String listJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(listJson, new TypeReference<Map<String, String>>() {});
        if (map.get("listJson") == null) {
            throw new RuntimeException("缺少请求参数");
        }

        List<Equivalent> list = JSONUtils.parseJsonToObject(map.get("listJson"),  new TypeReference<List<Equivalent>>() {});
        equivalentService.saveList(list);
        return PageResult.success();
    }

    /**
     * 保存操作
     * @param equivalent
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Equivalent equivalent, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        this.equivalentService.save(equivalent);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param equivalent
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Equivalent equivalent, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        this.equivalentService.update(equivalent);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Integer id) {
        this.equivalentService.delete(id);
        return PageResult.success();
    }

    /**
     * 根据条件获取对象的集合
     * @param pageNum
     * @param pageSize
     * @param useGsmGoodsName
     * @param referGsmGoodsName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String useGsmGoodsName,
            @RequestParam(required = false) String referGsmGoodsName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = equivalentService.getByCriteria(useGsmGoodsName, referGsmGoodsName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }











}
