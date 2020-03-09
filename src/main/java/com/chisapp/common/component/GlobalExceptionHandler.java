package com.chisapp.common.component;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 * 所有异常处理结果均封装成 PageResult 对象以 JSON 格式返回
 * 处理规则: 优先精确匹配进行处理
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:45
 * @Version 1.0
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    /***
     * Shiro 异常处理
     * AuthenticationException(父类) 认证失败需要抛出的异常。
     *     IncorrectCredentialsException 不正确的凭证
     *     ExpiredCredentialsException 凭证过期
     *     AccountException 账号异常
     *     ConcurrentAccessException 并发访问异常（多个用户同时登录时抛出）
     *     UnknownAccountException 未知的账号
     *     ExcessiveAttemptsException 认证次数超过限制
     *     DisabledAccountException 禁用的账号
     *     LockedAccountException 账号被锁定
     *     UnsupportedTokenException 使用了不支持的Token
     * AuthorizationException(父类) 没有访问权限抛出的异常
     *     UnauthorizedException:抛出以指示请求的操作或对请求的资源的访问是不允许的。
     */

    /**
     * Shiro 未知账户
     * 401
     */
    @ExceptionHandler({UnknownAccountException.class})
    public PageResult handleUnknownAccountException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("账户或密码错误");
    }

    /**
     * Shiro 账户验证错误
     * 401
     */
    @ExceptionHandler({IncorrectCredentialsException.class})
    public PageResult handleIncorrectCredentialsException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("账户或密码错误");
    }

    /**
     * Shiro 账户被锁定
     * 401
     */
    @ExceptionHandler({LockedAccountException.class})
    public PageResult handleLockedAccountException(Exception e, HttpServletResponse response){
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("您的账号已被禁用,请联系管理员");
    }

    /**
     * Shiro 账户并发登录
     * 401
     */
    @ExceptionHandler({ConcurrentAccessException.class})
    public PageResult handleConcurrentAccessException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("您的账号已在其他地方登录,如非本人操作请及时修改密码!!!");
    }

    /**
     * Shiro 凭证过期
     * 401
     */
    @ExceptionHandler({ExpiredCredentialsException.class})
    public PageResult handleExpiredCredentialsException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("您的凭证失效,请重新登录");
    }

    /**
     * Shiro 其他身份验证异常
     * 401
     */
    @ExceptionHandler({AuthenticationException.class})
    public PageResult handlerAuthenticationException(Exception e, HttpServletResponse response){
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg(e.getMessage());
    }

    /**
     * Shiro 没有权限
     * 403
     */
    @ExceptionHandler({UnauthorizedException.class})
    public PageResult handleUnauthorizedException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_FORBIDDEN).msg("没有权限");
    }

    /**
     * Shiro 其他访问权限异常
     * 403
     */
    @ExceptionHandler({AuthorizationException.class})
    public PageResult handleAuthorizationException(Exception e, HttpServletResponse response){
        return PageResult.fail().code(HttpServletResponse.SC_FORBIDDEN).msg(e.getMessage());
    }

    /**
     * Shiro 所有异常
     */
    @ExceptionHandler({ShiroException.class})
    public PageResult handleShiroException(Exception e, HttpServletResponse response){
        e.printStackTrace();
        return PageResult.fail().msg(e.getMessage());
    }

    /**
     * JWT 创建异常
     * 401
     */
    @ExceptionHandler({JWTCreationException.class})
    public PageResult handleJWTCreationException() {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("凭证创建失败");
    }

    /**
     * JWT 解码异常
     * 401
     */
    @ExceptionHandler({JWTDecodeException.class})
    public PageResult handleJWTDecodeException() {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("凭证失效");
    }

    /**
     * JWT 校验异常
     * 401
     * @return
     */
    @ExceptionHandler({JWTVerificationException.class})
    public PageResult handleJWTVerificationException() {
        return PageResult.fail().code(HttpServletResponse.SC_UNAUTHORIZED).msg("凭证失效");
    }

    /**
     * 缺少请求参数
     * 412
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public PageResult handleMissingServletRequestParameterException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_PRECONDITION_FAILED).msg("缺少请求参数");
    }

    /**
     * 请求方式错误
     * 405
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public PageResult handleHttpRequestMethodNotSupportedException(Exception e, HttpServletResponse response) {
        return PageResult.fail().code(HttpServletResponse.SC_METHOD_NOT_ALLOWED).msg("请求方式错误");
    }

    /**
     * SQL 违反唯一约束
     * 403
     * @return
     */
    @ExceptionHandler({DuplicateKeyException.class})
    public PageResult handleDuplicateKeyException() {
        return PageResult.fail().code(HttpServletResponse.SC_FORBIDDEN).msg("该记录已经存在");
    }

    /**
     * SQL 违反数据约束
     * 403
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public PageResult handleDataIntegrityViolationException() {
        return PageResult.fail().code(HttpServletResponse.SC_FORBIDDEN).msg("违反数据约束");
    }

    /**
     * 捕获所有异常
     */
    @ExceptionHandler({Exception.class})
    public PageResult handleAllException(Exception e){
        e.printStackTrace();
        return PageResult.fail().msg(e.getMessage() == null ? e.getClass().getName() : e.getMessage());
    }
}
