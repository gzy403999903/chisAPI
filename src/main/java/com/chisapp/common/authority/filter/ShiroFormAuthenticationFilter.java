package com.chisapp.common.authority.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 重写了原 FormAuthenticationFilter 过滤器的 onAccessDenied 方法
 * 使其在未登陆成功时直接重定向到登出界面 (主要用于在前端 TOKEN 未过期的情况下 登出操作返回 401 清除 TOKEN)
 *
 * @Author: Tandy
 * @Date: 2019/7/5 11:16
 * @Version 1.0
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            // saveRequestAndRedirectToLogin(request, response);
            // 非登录操作重定向到登出操作
            WebUtils.issueRedirect(request, response, "/logout");
            return false;
        }
    }
}
