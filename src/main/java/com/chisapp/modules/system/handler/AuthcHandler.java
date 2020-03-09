package com.chisapp.modules.system.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.component.TreeNode;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.system.bean.Authc;
import com.chisapp.modules.system.service.AuthcService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/21 18:44
 * @Version 1.0
 */
@RequestMapping("/authc")
@RestController
public class AuthcHandler {

    private AuthcService authcService;
    @Autowired
    public void setAuthcService(AuthcService authcService) {
        this.authcService = authcService;
    }

    /**
     * 更新角色权限
     * @param roleName
     * @param checkedAuthcJson
     * @return
     */
    @PutMapping("/updateRoleNames")
    public PageResult updateRoleNames (@RequestParam("roleName") String roleName,
                                       @RequestParam("checkedAuthcJson") String checkedAuthcJson) {
        List<Authc> authcList = authcService.getAll();
        HashSet<Integer> checkedAuthcSet = JSONUtils.parseJsonToObject(checkedAuthcJson, new TypeReference<HashSet<Integer>>() {});
        authcService.updateRoleNames(authcList, roleName, checkedAuthcSet);
        return PageResult.success();
    }

    /**
     * 获取 Tree 数据
     * @return
     */
    @GetMapping("/getTree")
    public List<TreeNode> getTree () {
        return authcService.getTree(authcService.getAll());
    }

}
