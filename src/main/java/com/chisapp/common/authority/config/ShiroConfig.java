package com.chisapp.common.authority.config;

import com.chisapp.common.authority.component.ShiroFilterChainDefinitionMap;
import com.chisapp.common.authority.component.ShiroModularRealmAuthenticator;
import com.chisapp.common.authority.filter.ShiroFormAuthenticationFilter;
import com.chisapp.common.authority.filter.ShiroRolesAuthorizationFilter;
import com.chisapp.common.authority.realm.ShiroRealm;
import com.chisapp.common.authority.realm.WeChatRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro 配置文件
 *
 * @Author: Tandy
 * @Date: 2019/5/8 14:12
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * 通过配置文件获取 redis 服务器属性
     */
    @Value("${spring.redis.host}:${spring.redis.port}")
    private String host;
    @Value("${spring.redis.password}")
    private String passowrd;
    @Value("${spring.redis.database}")
    private Integer database;

    /**
     * 配置 Realm
     * @return
     */
    @Bean("shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    @Bean("weChatRealm")
    public WeChatRealm weChatRealm () {
        return new WeChatRealm();
    }

    /**
     * 配置 Realm 管理器, 主要针对多 Realm 时的认证策略
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator () {
        ShiroModularRealmAuthenticator modularRealmAuthenticator = new ShiroModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }


    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        if ( passowrd != null && !passowrd.equals("")) {
            redisManager.setPassword(passowrd);
        }
        redisManager.setDatabase(database);
        return redisManager;
    }

    /**
     * 配置 cacheManager (缓存管理器)
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置 sessionDao
     * 使用的是shiro-redis开源插件
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        // shiro 的 session 被 redis 持久化额过期时间(单位秒)
        redisSessionDAO.setExpire(10800); // 10800秒 = 3小时
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    /**
     * 配置 sessionManager
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置 session 过期时间, 配置此处会覆盖 spring boot session 有效期
        // 这里设置的时间单位是:ms(毫秒)，但是Shiro会把这个时间转成:s，而且是会舍掉小数部分，这样设置的是-1ms，转成s后就是0s，马上就过期了。所有要是除以1000以后还是负数，必须设置小于-1000
        // 如果使用了 redis 缓存了 session ,两个过期时间同时有效, 先到的将停用 session
        sessionManager.setGlobalSessionTimeout(-10001);
        // 设置是否在 URL 后添加 sessionID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 设置删除无效的 session 对象, 如果设置成 false 则需要指定 session 定时调度器来删除
        sessionManager.setDeleteInvalidSessions(true);
        // 指定 session 定时调度器
        // sessionManager.setSessionValidationScheduler(null);
        // 设置开启定时调度器进行检测
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 配置用于持久化 session 工具类
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 配置 SecurityManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // realm 认证策略
        securityManager.setAuthenticator(modularRealmAuthenticator());

        // 单 realm 注册
        // securityManager.setRealm(shiroRealm());

        // 多 realm 注册
        List<Realm> realms = new ArrayList<>();
        realms.add(shiroRealm());
        realms.add(weChatRealm());
        securityManager.setRealms(realms);

        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    @Autowired
    private ShiroFilterChainDefinitionMap shiroFilterChainDefinitionMap;
    /**
     * 配置 ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 注册自定义 filter , 如果注册名以经存在则 自定义的 filter 替换对应的 filter, 如果没有则进行添加
        // 此处使用自定义的 filter 替换了原有的 以 authc、roles 注册的 filter
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new ShiroFormAuthenticationFilter());
        filters.put("roles", new ShiroRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 配置 FilterChainDefinitionMap 加载需要管理的权限路径
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinitionMap.getMap());

        return shiroFilterFactoryBean;
    }

    /**
     * Shiro生命周期处理器
     * 该方法要配置为静态方法  否则在启动时其他属性不能进行依赖注入
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 注解权限
     * 解决使用注解控制权限不起作用的问题, 注解要标注到 @Controller
     * 如果标注到 @service, 当和 @Transactional 标注同一个方法时, 会报错,
     * 因为该方法已经为代理对象, 该方法不能是代理的代理。
     */
    /*
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
    */

    /**
     * filterChainDefinitionMap 权限
     * setUsePrefix(true) 解决在使用非注解权限时报错的问题
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setUsePrefix(true);
        return creator;
    }

}
