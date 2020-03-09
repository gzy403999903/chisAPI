package com.chisapp.common.interceptor;

import com.chisapp.common.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于拦截访问是否携带了有效的 Token
 * 如果携带了 Token 则验证其是否合法、是否过期
 * 如果以上两项验证通过 则验证 session 是否处于有效状态
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:32
 * @Version 1.0
 */
public class AccessTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取访问头中的 token 内容
        String accessToken = request.getHeader("Authorization");
        // 校验 token 是否合法和是否过期
        JWTUtils.getInstance().verifyToken(accessToken);
        return true;
    }
}
