package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.SecondClassify;
import com.chisapp.modules.system.service.SecondClassifyService;
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
 * @Date: 2020/2/10 20:54
 * @Version 1.0
 */
@RequestMapping("/secondClassify")
@RestController
public class SecondClassifyHandler {

    private SecondClassifyService secondClassifyService;
    @Autowired
    public void setSecondClassifyService(SecondClassifyService secondClassifyService) {
        this.secondClassifyService = secondClassifyService;
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
            map.put("secondClassify", secondClassifyService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param secondClassify
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid SecondClassify secondClassify, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        secondClassifyService.save(secondClassify);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param secondClassify
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid SecondClassify secondClassify, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        secondClassifyService.update(secondClassify);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param secondClassify
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (SecondClassify secondClassify) {
        secondClassifyService.delete(secondClassify);
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
            @RequestParam(required = false) Integer goodsClassifyId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean state){
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = secondClassifyService.getByCriteria(goodsClassifyId, name, state);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取所有启用状态的二级分类集合
     * @return
     */
    @GetMapping("/getEnabledByGoodsClassifyId")
    public PageResult getEnabledByGoodsClassifyId (@RequestParam Integer goodsClassifyId) {
        List<Map<String, Object>> list = secondClassifyService.getEnabledByGoodsClassifyId(goodsClassifyId);
        return PageResult.success().resultSet("list", list);
    }

}
