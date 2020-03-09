package com.chisapp.common.authority.filter;

import com.chisapp.common.authority.component.ShiroSessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 替换原 RolesAuthorizationFilter 过滤器
 * 主要实现以下功能
 * 1 原 RolesAuthorizationFilter 需要匹配所有的 roles 才能通过
 *      例如 roles[a,b,c] 用户需要同时具备 a,b,c 这3个分组才能通过匹配
 *      而 ShiroRolesAuthorizationFilter 只需要匹配其中一个分组就可以
 *
 * 2 当用户登录 Session 失效后, 进行访问需要授权的路径时不会跳转登录页面
 *      而是跳转到登出路径进行 JSON 数据返回
 *
 * @Author: Tandy
 * @Date: 2019/5/8 16:17
 * @Version 1.0
 */
public class ShiroRolesAuthorizationFilter extends AuthorizationFilter {

    private WebApplicationContext applicationContext = null;
    private ShiroSessionManager shiroSessionManager = null;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);

        // 判断是否重复登录用户
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        shiroSessionManager = applicationContext.getBean(ShiroSessionManager.class);
        if (shiroSessionManager.isRepeatLogin(subject)) {
            return false;
        }

        // 判断角色权限
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return false;
        }

        for(int i = 0; i < rolesArray.length; i++){
            if(subject.hasRole(rolesArray[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * 当 session 失效后如果继续进行访问 不在转到登录页面
     * 而是转到指定路径做出相应的处理
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // session 失效进行登出操作
        if (subject.getPrincipal() == null) {
            WebUtils.issueRedirect(request, response, "/logout");
        } else if (shiroSessionManager.isRepeatLogin(subject)) {
            // 如果当前用户为强制下线用户则进行强制下线操作
            shiroSessionManager.removeRepeatLogin(subject);
            WebUtils.issueRedirect(request, response, "/forcedLogout");
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
