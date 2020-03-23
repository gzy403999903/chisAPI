package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.PracticeScope;
import com.chisapp.modules.system.service.PracticeScopeService;
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
 * @Date: 2020-03-22 19:14
 * @Version 1.0
 */
@RequestMapping("/practiceScope")
@RestController
public class PracticeScopeHandler {

    private PracticeScopeService practiceScopeService;
    @Autowired
    public void setPracticeScopeService(PracticeScopeService practiceScopeService) {
        this.practiceScopeService = practiceScopeService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

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
            map.put("practiceScope", practiceScopeService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param practiceScope
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid PracticeScope practiceScope, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        practiceScopeService.save(practiceScope);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param practiceScope
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid PracticeScope practiceScope, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        practiceScopeService.update(practiceScope);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param practiceScope
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (PracticeScope practiceScope) {
        practiceScopeService.delete(practiceScope);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer practiceTypeId,
            @RequestParam(required = false) String name){
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = practiceScopeService.getByCriteria(practiceTypeId, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据职业类型获取对应的职业范围
     * @return
     */
    @GetMapping("/getByPracticeTypeId")
    public PageResult getByPracticeTypeId (@RequestParam Integer practiceTypeId) {
        List<PracticeScope> list = practiceScopeService.getByPracticeTypeId(practiceTypeId);
        return PageResult.success().resultSet("list", list);
    }



}
