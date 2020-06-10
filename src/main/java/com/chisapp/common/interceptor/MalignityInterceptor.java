package com.chisapp.common.interceptor;

import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Tandy
 * @Date: 2020-06-09 14:38
 * @Version 1.0
 */
public class MalignityInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        // 获取访问者的 IP
        String ip = this.getRealIp(request);

        // 判断是否登陆
        if (user == null) {
            // 如果 60 秒内, 访问超过 5 次为频繁访问, 访问超过 10 次锁定
            this.requestJudge(ip, 60, 5, 10);
        } else {
            // 如果 60 秒内, 访问超过 30 次为频繁访问, 访问超过 50 次锁定
            this.requestJudge(ip, 60, 30, 50);
        }

        return true;
    }

    private void requestJudge (String ip, long timeout, int frequentlyCount, int lockCount) {
        // 获取该 ip 对应的缓存访问次数
        String cacheCount = stringRedisTemplate.opsForValue().get(ip);
        // 如果获取不到则添加一个, 设置访问次数为 1, 有效时长为  秒
        if (cacheCount == null) {
            stringRedisTemplate.opsForValue().set(ip, "1");
            stringRedisTemplate.expire(ip, timeout, TimeUnit.SECONDS);
        } else {
            // 如果获取到访问次数则进行以下操作
            // 将记录的访问次数加1
            stringRedisTemplate.opsForValue().increment(ip);
            // 获取记录的访问次数
            int recordCount = Integer.parseInt(cacheCount);

            // 设定一个过渡时间
            if (recordCount == frequentlyCount) {
                stringRedisTemplate.expire(ip, timeout * 10, TimeUnit.SECONDS);
            }

            // 判断请求是否过于频繁
            if (recordCount > frequentlyCount && recordCount < lockCount) {
                Long residueTimeout = stringRedisTemplate.getExpire(ip);
                throw new RuntimeException("您的请求过于频繁, 请在" + residueTimeout + "秒后重试.");
            }

            // 判断请求是否过于频繁, 并使该 IP 在缓存中一直有效
            if (recordCount >= lockCount) {
                stringRedisTemplate.persist(ip);
                throw new RuntimeException("您已被禁止访问, 如有疑问请联系管理员.");
            }
        }
    }

    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
