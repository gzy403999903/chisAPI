package com.chisapp.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密生成器
 *
 * @Author: Tandy
 * @Date: 2019/5/8 9:54
 * @Version 1.0
 */
public class EncryptionUtils {

    /**
     * 获取 shiro 盐值加密码
     * 这个方法的加密参数值 比如 加密方式 和 加密次数 要与 Shiro Realm 中的加密参数值一致
     * 否则无法进行匹配登录
     * @param sourceStr 进行加密的字符串
     * @param saltStr 盐值
     * @return
     */
    public static synchronized String getShiroSaltCode(String sourceStr, String saltStr) {
        String algorithmName = "MD5"; // 加密方式
        Object source = sourceStr;
        Object salt = ByteSource.Util.bytes(saltStr);
        int hashIterations = 100; // 要加密的次数
        Object result = new SimpleHash(algorithmName, source, salt, hashIterations);

        return result.toString();
    }

}
