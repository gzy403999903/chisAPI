package com.chisapp.modules.doctorworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.doctorworkstation.bean.CommonDiagnose;
import com.chisapp.modules.doctorworkstation.service.CommonDiagnoseService;
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
 * @Date: 2019/11/6 16:20
 * @Version 1.0
 */
public class CommonDiagnoseHandler {

    protected Integer DWT_DIAGNOSE_TYPE_ID = null; // 诊断类型ID

    private CommonDiagnoseService commonDiagnoseService;
    @Autowired
    public void setCommonDiagnoseService(CommonDiagnoseService commonDiagnoseService) {
        this.commonDiagnoseService = commonDiagnoseService;
    }

    @ModelAttribute
    public void getById(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        if (id != null) {
            map.put("commonDiagnose", commonDiagnoseService.getById(id));
        }
    }

    /**
     * 保存诊断
     * @param commonDiagnose
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid CommonDiagnose commonDiagnose, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        commonDiagnose.setDwtDiagnoseTypeId(DWT_DIAGNOSE_TYPE_ID);
        commonDiagnoseService.save(commonDiagnose);
        return PageResult.success();
    }

    /**
     * 保存规范诊断
     * @param commonDiagnose
     * @param result
     * @return
     */
    @PostMapping("/saveNormative")
    public PageResult saveNormative(@Valid CommonDiagnose commonDiagnose, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        commonDiagnose.setShareState(true);
        commonDiagnose.setNormative(true);
        commonDiagnose.setDwtDiagnoseTypeId(DWT_DIAGNOSE_TYPE_ID);
        commonDiagnoseService.save(commonDiagnose);
        return PageResult.success();
    }

    /**
     * 修改自定义诊断
     * @param commonDiagnose
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid CommonDiagnose commonDiagnose, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }

        commonDiagnoseService.update(commonDiagnose);
        return PageResult.success();
    }

    /**
     * 删除操作
     */
    @DeleteMapping("/delete")
    public PageResult delete(@RequestParam("id") Integer id) {
        commonDiagnoseService.delete(id);
        return PageResult.success();
    }

    /**
     * 根据查询条件进行分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @param shareState
     * @return
     */
    @GetMapping("/getByCriteria")
    public PageResult getByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam Boolean shareState){

        User user = (User) SecurityUtils.getSubject().getPrincipal();  // 获取用户信息
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = commonDiagnoseService.getByCriteria(user.getId(), DWT_DIAGNOSE_TYPE_ID, shareState, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据诊断名称或助记码进行检索查询
     */
    @GetMapping("/getLikeByName")
    public List<CommonDiagnose> getLikeByName(@RequestParam("name") String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return commonDiagnoseService.getLikeByName(DWT_DIAGNOSE_TYPE_ID, name);
    }

}
