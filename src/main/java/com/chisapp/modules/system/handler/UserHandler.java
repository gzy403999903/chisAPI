package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSRMessageUtils;
import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.service.UserService;
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
 * @Date: 2019/7/20 21:58
 * @Version 1.0
 */
@RequestMapping("/user")
@RestController
public class UserHandler {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
            map.put("user", userService.getById(id));
        }
    }

    /**
     * 保存操作
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        userService.save(user);
        return PageResult.success();
    }

    /**
     * 修改操作
     * @param user
     * @param result
     * @return
     */
    @PutMapping("/update")
    public PageResult update(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return PageResult.fail().msg(JSRMessageUtils.getFirstMsg(result));
        }
        userService.update(user);
        return PageResult.success();
    }

    /**
     * 删除操作
     * @param user
     * @return
     */
    @DeleteMapping("/delete")
    public PageResult delete(User user) {
        userService.delete(user);
        return PageResult.success();
    }

    /**
     * 初始化密码
     * @param user
     * @return
     */
    @PutMapping("/initPassword")
    public PageResult initPassword(User user) {
        userService.initPassword(user);
        return PageResult.success();
    }

    /**
     * 修改密码
     * @param oldPass
     * @param pass
     * @return
     */
    @PutMapping("/updatePassword")
    public PageResult updatePassword(@RequestParam("oldPass") String oldPass,
                                     @RequestParam("pass") String pass) {
        User caheUser = (User) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getById(caheUser.getId());
        userService.updatePassword(user, oldPass, pass);
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
    public PageResult getByCriteria(
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam String name,
            @RequestParam String sysRoleName,
            @RequestParam String sysClinicName,
            @RequestParam(required = false) Boolean state){
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = userService.getByCriteria(name, sysRoleName, sysClinicName, state);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取对应机构启用状态的用户
     * @return
     */
    @GetMapping("/getClinicEnabled")
    public List<Map<String, Object>> getClinicEnabled() {
        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return userService.getClinicEnabled(user.getSysClinicId());
    }

}
