package com.chisapp.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.chisapp.modules.system.bean.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建、核验 Token 的工具类
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:09
 * @Version 1.0
 */
public class JWTUtils {
    private JWTUtils() {}

    private static volatile JWTUtils instance;

    public static JWTUtils getInstance() {
        if(instance == null) {
            synchronized (JWTUtils.class) {
                if(instance == null) {
                    instance = new JWTUtils();
                }
            }
        }
        return instance;
    }

    // token 的前缀
    private final String PREFIX = "Bearer ";
    // token 密匙,不能泄露,不能随意修改
    private final String SECRET = "Tandy55312153";
    // token 过期时间
    private final int calendarField = Calendar.MONTH; //Calendar.DATE;
    private final int calendarInterval = 1;

    /**
     * 添加 token 前缀
     * @param token
     * @return
     */
    private String addPrefix(String token) {
        return PREFIX + token;
    }

    /**
     * 去除 token 前缀
     * @param token
     * @return
     */
    private String removePrefix(String token) {
        if(token == null) {
            throw new JWTDecodeException("token不能为空");
        }
        if(!token.startsWith(PREFIX)) {
            throw new JWTDecodeException("没有指定的前缀");
        }
        return token.substring(PREFIX.length()).trim();
    }


    /**
     * 创建 token
     * Payload 标准中注册的声明 (建议但不强制使用)
     *     iss: jwt签发者
     *     sub: jwt所面向的用户
     *     aud: 接收jwt的一方
     *     exp: jwt的过期时间，这个过期时间必须要大于签发时间
     *     iat: jwt的签发时间
     *     nbf: 定义在什么时间之前，该jwt都是不可用的.
     *     jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     * @param user
     * @return
     */
    public String produceToken(User user) {
        if(user == null) {
            throw new JWTCreationException("user 不能为空", null);
        }

        // 设置头部信息
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // 创建 token
        String token = JWT.create().withHeader(header) // header
                .withIssuer("ehm-server") // iss
                .withSubject("ehm-ui") // sub
                .withIssuedAt(new Date()) // ida
                .withExpiresAt(expiresDate) // exp
                .withAudience(user.getAccount()) // aud
                .withClaim("userId", user.getId()) // 用户ID
                .withClaim("userName", user.getName()) // 用户姓名
                .withClaim("roleId", user.getRole().getId()) // 分组ID
                .withClaim("roleName", user.getRole().getName()) // 分组名称
                .withClaim("clinicId", user.getClinic().getId()) // 门诊ID
                .withClaim("clinicName", user.getClinic().getName()) // 门诊名称
                .withClaim("clinicTel", user.getClinic().getTel()) // 门诊名称
                .withClaim("clinicAddress", user.getClinic().getAddress()) // 门诊名称
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return addPrefix(token);
    }

    /**
     * 校验 token
     * 校验其合法性和是否过期, 任何一项出现不合法都会抛出对应的异常交由全局异常处理器进行处理
     * @param token
     * @return
     */
    public void verifyToken(String token) {
        token = removePrefix(token);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        verifier.verify(token);
    }
}
