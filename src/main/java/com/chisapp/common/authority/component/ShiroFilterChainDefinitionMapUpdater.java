package com.chisapp.common.authority.component;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Shiro 权限的加载器
 * 当权限发生改变时调用此类的 update 方法可动态更新权限
 *
 * @Author: Tandy
 * @Date: 2019/5/8 18:01
 * @Version 1.0
 */
@Component
public class ShiroFilterChainDefinitionMapUpdater {

    @Autowired
    private ShiroFilterChainDefinitionMap filterChainDefinitionMap;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    public synchronized void update() {
        try {
            AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver =
                    (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager =
                    (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空初始权限配置
            manager.getFilterChains().clear();

            // 重新获取资源
            Map<String, String> chains = filterChainDefinitionMap.getMap();

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
