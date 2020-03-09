package com.chisapp.common.authority.component;

/**
 * 枚举要使用 Realm 名称中包含的单词
 * 例如 shiroRealm 枚举为 SHIRO
 *      wechatReal 枚举为 WECHAT
 * @Author: Tandy
 * @Date: 2020/3/2 12:59
 * @Version 1.0
 */
public enum LoginTypeEnum {
    SHIRO, // 正常登录
    WECHAT // 微信
}
