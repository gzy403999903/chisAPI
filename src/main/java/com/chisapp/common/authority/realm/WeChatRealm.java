package com.chisapp.common.authority.realm;

import com.chisapp.common.utils.HttpUtils;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.utils.URIBuilder;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

import java.net.URI;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/29 17:50
 * @Version 1.0
 */
public class WeChatRealm extends AuthenticatingRealm {

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取微信临时凭证
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        // 向微信服务器进行验证
        String dataJson = null;
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.weixin.qq.com")
                    .setPath("/sns/jscode2session")
                    .setParameter("appid", "wx3a3f4ab7e4d99add")
                    .setParameter("secret", "7271d8e8a89352eb1bf9b614c5fb0809")
                    .setParameter("js_code", username)
                    .setParameter("grant_type", "authorization_code")
                    .build();

            // 获取返回的结果
            dataJson = HttpUtils.doGet(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据返回结果进行认证
        User user;
        Map<String, String> data = JSONUtils.parseJsonToObject(dataJson, new TypeReference<Map<String, String>>() {});
        if (data.get("openid") != null) {
            // 根据 openid 获取会员 生成虚拟用户并赋值必填属性
            user = new User();
            user.setId(-1);
            user.setName("会员名字");
            user.setSysRoleId(1); // 权限分组ID
        } else {
            throw new UnknownAccountException("账户不存在");
        }
        System.out.println("微信服务器返回数据 ---- > " + data);

        // 验证用户(可以是对象, 也可以是 username)
        Object principal = user;
        // 需要对比的密码(从数据库获取到的密码)
        Object credentials = user.getPassword();
        // 当前 Realm 类名
        String realmName = getName();

        return new SimpleAuthenticationInfo(principal, credentials, realmName);
    }
}
