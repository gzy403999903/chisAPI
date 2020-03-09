package com.chisapp.common.authority.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Shiro 权限自动加载器
 * 当 ShiroConfig 中的  public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() 方法为非静态方式时
 * 不能自动注册 ShiroFilterChainDefinitionMap 这时需要在程序启动后通过该加载器对 setFilterChainDefinitionMap 进行重载
 *
 * @Author: Tandy
 * @Date: 2019/5/8 18:01
 * @Version 1.0
 */
@Component
@Order(1) // 设置启动优先级 越小越优先
public class ShiroFilterChainDefinitionMapReloader implements ApplicationRunner {

    @Autowired
    private ShiroFilterChainDefinitionMapUpdater updater;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // updater.update();
    }
}
