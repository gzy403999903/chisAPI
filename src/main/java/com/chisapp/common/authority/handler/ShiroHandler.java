package com.chisapp.common.authority.handler;

import com.chisapp.common.authority.component.LoginTypeEnum;
import com.chisapp.common.authority.component.ShiroSessionManager;
import com.chisapp.common.authority.component.ShiroUsernamePasswordToken;
import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JWTUtils;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shiro 的鉴权管理
 *
 * 该类中的方法不要涉及到业务操作
 * 所有方法的访问路径应在 AccessTokenInterceptor 拦截器排除
 * 并在 ShiroFilterChainDefinitionMap 中添加对应的匿名访问权限
 *
 * 该类主要为 登录操作、登出操作、强制登出操作
 *
 * @Author: Tandy
 * @Date: 2019/5/8 16:03
 * @Version 1.0
 */
@RestController
public class ShiroHandler {

    private ShiroSessionManager shiroSessionManager;
    @Autowired
    public void setShiroSessionManager(ShiroSessionManager shiroSessionManager) {
        this.shiroSessionManager = shiroSessionManager;
    }

    @Value("${chis-single-login}")
    private Boolean chisSingleLogin = true;

    /**
     * 登录操作
     * 登录成功后返回一个 JSON 格式的 PageResult 对象
     */
    @PostMapping("/login")
    public PageResult login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "loginType", required = false) String loginType) {

        // 如果未携带登录类型参数则默认为普通登录
        if (loginType == null) {
            loginType = LoginTypeEnum.SHIRO.toString();
        }

        // 认证用户
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new ShiroUsernamePasswordToken(username, password, loginType);
            token.setRememberMe(false);// 记住我功能 true开启 , false关闭
            currentUser.login(token);
        }

        // 开启单点登录
        if (this.chisSingleLogin) {
            shiroSessionManager.singleLogin(currentUser);
        }

        // 生成 Token 并返回
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String accessToken = JWTUtils.getInstance().produceToken(user);
        return PageResult.success().code(226).resultSet("Authorization", accessToken);
    }

    /**
     * 无权限处理
     * 抛出异常交由异常全局处理器
     */
    @RequestMapping("/unauthorized")
    public void unauthorized() {
        throw new UnauthorizedException();
    }

    /**
     * 正常登出 或 session 失效登出
     */
    @RequestMapping("/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (shiroSessionManager.isRepeatLogin(subject)) {
            shiroSessionManager.removeRepeatLogin(subject);
        }
        subject.logout();
        throw new ExpiredCredentialsException();
    }

    /**
     * 强制下线
     * 交由异常进行返回
     */
    @RequestMapping("/forcedLogout")
    public void forcedLogout() {
        SecurityUtils.getSubject().logout();
        throw new ConcurrentAccessException();
    }

}
