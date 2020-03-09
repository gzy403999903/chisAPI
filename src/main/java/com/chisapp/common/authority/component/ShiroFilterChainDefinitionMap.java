package com.chisapp.common.authority.component;

import com.chisapp.modules.system.bean.Authc;
import com.chisapp.modules.system.service.AuthcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置 Shiro 访问权限
 *
 * @Author: Tandy
 * @Date: 2019/5/8 17:09
 * @Version 1.0
 */
@Component
public class ShiroFilterChainDefinitionMap {

    /**
     * 过滤器：
     * anon	                org.apache.shiro.web.filter.authc.AnonymousFilter
     * authc	            org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * authcBasic	        org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * logout	            org.apache.shiro.web.filter.authc.LogoutFilter
     * noSessionCreation	org.apache.shiro.web.filter.session.NoSessionCreationFilter
     * perms	            org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
     * port	                org.apache.shiro.web.filter.authz.PortFilter
     * rest	                org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * roles	            org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * ssl  	            org.apache.shiro.web.filter.authz.SslFilter
     * user	                org.apache.shiro.web.filter.authc.UserFilter
     *
     * 配置权限 顺序从上而下执行
     */

    @Autowired
    private AuthcService authcService;

    public Map<String,String> getMap() {
        Map<String,String> map = new LinkedHashMap<>();
        List<Authc> authcList = authcService.getAll();

        // 配置匿名访问权限
        map.put("/login/**", "anon");
        map.put("/unauthorized/**", "anon");
        map.put("/logout/**", "anon");
        map.put("/forcedLogout/**", "anon");

        // 配置访问权限
        for (Authc authc : authcList) {
            if(authc.getPathIndex() != null && authc.getRoleNames() != null) {
                map.put(authc.getPathIndex(), "roles[" + authc.getRoleNames() + "]");
            }
        }

        // 配置所有权限都需要进行登录才能访问
        map.put("/**", "authc");

        return map;
    }
}
