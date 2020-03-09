package com.chisapp.modules.member.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.member.bean.MemberType;
import com.chisapp.modules.member.service.MemberTypeService;
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
 * @Date: 2019/10/19 18:25
 * @Version 1.0
 */
@RequestMapping("/memberType")
@RestController
public class MemberTypeHandler {

    private MemberTypeService memberTypeService;
    @Autowired
    public void setMemberTypeService(MemberTypeService memberTypeService) {
        this.memberTypeService = memberTypeService;
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
            map.put("memberType", memberTypeService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param memberType
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid MemberType memberType, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        memberTypeService.save(memberType);
        return PageResult.success();
    }

    /**
     * 编辑操作
     * @param memberType
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid MemberType memberType, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        memberTypeService.update(memberType);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete(MemberType memberType) {
        memberTypeService.delete(memberType.getId());
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
        List<MemberType> pageList = memberTypeService.getByCriteria(name, state);
        PageInfo<MemberType> pageInfo = new PageInfo<MemberType>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取所有启用的会员类型
     * @return
     */
    @GetMapping("/getEnabled")
    public List<MemberType> getEnabled() {
        return memberTypeService.getEnabled();
    }






}
