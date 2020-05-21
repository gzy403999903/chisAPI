package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.financial.bean.WorkGroup;
import com.chisapp.modules.financial.service.WorkGroupService;
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
 * @Date: 2020-05-15 16:33
 * @Version 1.0
 */
@RequestMapping("/workGroup")
@RestController
public class WorkGroupHandler {

    @Autowired
    private WorkGroupService workGroupService;

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
            map.put("workGroup", workGroupService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param workGroup
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid WorkGroup workGroup, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        workGroupService.save(workGroup);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param workGroup
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid WorkGroup workGroup, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        workGroupService.update(workGroup);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete(WorkGroup workGroup) {
        workGroupService.delete(workGroup.getId());
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @param state
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean state){

        PageHelper.startPage(pageNum, pageSize);
        List<WorkGroup> pageList = workGroupService.getByCriteria(name, state);
        PageInfo<WorkGroup> pageInfo = new PageInfo<WorkGroup>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取启用的集合
     * @return
     */
    @GetMapping("/getEnabled")
    public List<WorkGroup> getEnabled() {
        return workGroupService.getEnabled();
    }





}
