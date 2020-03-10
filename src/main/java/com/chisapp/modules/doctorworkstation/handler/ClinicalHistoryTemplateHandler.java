package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.doctorworkstation.bean.ClinicalHistoryTemplate;
import com.chisapp.modules.doctorworkstation.service.ClinicalHistoryTemplateService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/16 14:34
 * @Version 1.0
 */
@RequestMapping("/clinicalHistoryTemplate")
@RestController
public class ClinicalHistoryTemplateHandler {

    private ClinicalHistoryTemplateService clinicalHistoryTemplateService;
    @Autowired
    public void setClinicalHistoryTemplateService(ClinicalHistoryTemplateService clinicalHistoryTemplateService) {
        this.clinicalHistoryTemplateService = clinicalHistoryTemplateService;
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
            map.put("clinicalHistoryTemplate", clinicalHistoryTemplateService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param clinicalHistoryTemplate
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid ClinicalHistoryTemplate clinicalHistoryTemplate, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicalHistoryTemplateService.save(clinicalHistoryTemplate);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param clinicalHistoryTemplate
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid ClinicalHistoryTemplate clinicalHistoryTemplate, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        clinicalHistoryTemplateService.update(clinicalHistoryTemplate);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (@RequestParam Integer id) {
      clinicalHistoryTemplateService.delete(id);
      return PageResult.success();
    }

    /**
     * 根据条件获取对应集合
     * @param pageNum
     * @param pageSize
     * @param dwtDiagnoseTypeId
     * @param shareState
     * @param name
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) Integer dwtDiagnoseTypeId,
            @RequestParam Boolean shareState,
            @RequestParam(required = false) String name){

        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                clinicalHistoryTemplateService.getByCriteria(user.getId(), dwtDiagnoseTypeId, shareState, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }



}
