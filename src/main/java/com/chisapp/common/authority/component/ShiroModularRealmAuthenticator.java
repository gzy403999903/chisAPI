package com.chisapp.common.authority.component;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 重写 ModularRealmAuthenticator 中的方法, 使其根据登录类型选择 realm
 * (目的是在多 realm 时, 可使用单 realm 进行鉴权)
 * @Author: Tandy
 * @Date: 2020/3/2 12:34
 * @Version 1.0
 */
public class ShiroModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断 getRealms() 是否返回空
        assertRealmsConfigured();
        // 获取所有 realm
        Collection<Realm> realms = getRealms();

        // 获取登录类型
        ShiroUsernamePasswordToken token = (ShiroUsernamePasswordToken) authenticationToken;
        String loginType = token.getLoginType();

        // 过滤使用哪个 realm 进行登录鉴权
        List<Realm> filterRealms = new ArrayList<>();
        for (Realm realm : realms) {
            if (realm.getName().toLowerCase().contains(loginType.toLowerCase())) {
                filterRealms.add(realm);
            }
        }

        if (filterRealms.size() == 1) {
            return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realms, authenticationToken);
        }
    }

}
