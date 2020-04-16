package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.ClinicSellTarget;
import com.chisapp.modules.system.service.ClinicSellTargetService;
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
 * @Date: 2020-04-16 15:11
 * @Version 1.0
 */
@RequestMapping("/clinicSellTarget")
@RestController
public class ClinicSellTargetHandler {

    private ClinicSellTargetService clinicSellTargetService;
    @Autowired
    public void setClinicSellTargetService(ClinicSellTargetService clinicSellTargetService) {
        this.clinicSellTargetService = clinicSellTargetService;
    }

    /**
     * 请求参数中带有 ID 的方法在被调用前都会先调用此方法
     * 如果 ID 部位空则会进行查询并填充 model
     * map 中的 key 必须为 model 类名首字母小写
     * @param id
     * @param map
     */
    /*
    @ModelAttribute
    public void getById (@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("clinicSellTarget", clinicSellTargetService.getById(id));
        }
    }
    */

    /**
     * 保存操作
     * @param clinicSellTarget
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid ClinicSellTarget clinicSellTarget, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicSellTargetService.saveOrUpdate(clinicSellTarget);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param clinicSellTarget
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid ClinicSellTarget clinicSellTarget, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicSellTargetService.saveOrUpdate(clinicSellTarget);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Integer id) {
        clinicSellTargetService.delete(id);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param sysClinicName
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String sysClinicName){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = clinicSellTargetService.getByCriteria(sysClinicName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }







}
