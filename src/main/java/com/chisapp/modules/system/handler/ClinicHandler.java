package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.Clinic;
import com.chisapp.modules.system.service.ClinicService;
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
 * @Date: 2019/7/19 9:54
 * @Version 1.0
 */
@RequestMapping("/clinic")
@RestController
public class ClinicHandler {

    private ClinicService clinicService;
    @Autowired
    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
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
            map.put("clinic", clinicService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param clinic
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Clinic clinic, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicService.save(clinic);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param clinic
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Clinic clinic, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicService.update(clinic);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public PageResult delete (@PathVariable("id")Integer id) {
        clinicService.delete(id);
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
            @RequestParam String name){
        PageHelper.startPage(pageNum, pageSize);
        List<Clinic> pageList = clinicService.getByCriteria(name);
        PageInfo<Clinic> pageInfo = new PageInfo<Clinic>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取所有机构
     * @return
     */
    @GetMapping("/getEnabled")
    public List<Clinic> getEnabled () {
        return clinicService.getEnabled();
    }

    /**
     * 根据名称或助记码检索机构
     * @param name
     * @return
     */
    @GetMapping("/getEnabledLikeByName")
    public PageResult getEnabledLikeByName (String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }

        List<Clinic> list = clinicService.getEnabledLikeByName(name);
        return PageResult.success().resultSet("list", list);
    }

}
