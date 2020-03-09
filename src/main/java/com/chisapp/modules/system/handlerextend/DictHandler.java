package com.chisapp.modules.system.handlerextend;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.Dict;
import com.chisapp.modules.system.service.DictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 此类不进行数据返回
 * 子类通过继承的方式调用父类方法进行数据返回
 *
 * @Author: Tandy
 * @Date: 2019/7/28 19:56
 * @Version 1.0
 */
public class DictHandler {

    protected Integer SYS_DICT_TYPE_ID = null;

    private DictService dictService;
    @Autowired
    public void setDictService(DictService dictService) {
        this.dictService = dictService;
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
            map.put("dict", dictService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param dict
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save (@Valid Dict dict, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        dict.setSysDictTypeId(SYS_DICT_TYPE_ID);
        dictService.save(dict);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param dict
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update (@Valid Dict dict, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        dictService.update(dict);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param dict
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete (Dict dict) {
        dictService.delete(dict);
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
            @RequestParam String name,
            @RequestParam(required = false) Boolean state){
        PageHelper.startPage(pageNum, pageSize);
        List<Dict> pageList = dictService.getByCriteria(name, SYS_DICT_TYPE_ID, state);
        PageInfo<Dict> pageInfo = new PageInfo<Dict>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取字典启用状态的集合
     * @return
     */
    @GetMapping("/getEnabledByTypeId")
    public List<Dict> getEnabledByTypeId () {
        return dictService.getEnabledByTypeId(SYS_DICT_TYPE_ID);
    }

    /**
     * 根据参数获取对应的字典集合 (模糊查询)
     */
    @GetMapping("getEnabledLikeByName")
    public List<Dict> getEnabledLikeByName (String name) {
        if (name == null || name.trim().equals("")) {
            return null;
        }
        return dictService.getEnabledLikeByName(SYS_DICT_TYPE_ID, name);
    }
}
