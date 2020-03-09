package com.chisapp.common.authority.component;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author: Tandy
 * @Date: 2020/3/2 12:53
 * @Version 1.0
 */
public class ShiroUsernamePasswordToken extends UsernamePasswordToken {

    public ShiroUsernamePasswordToken(final String username, final String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    private String loginType;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
