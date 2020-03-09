package com.chisapp.common.config;

import com.chisapp.common.interceptor.AccessTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot 配置文件
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:28
 * @Version 1.0
 */
@Configuration
public class SpringBootConfig implements WebMvcConfigurer {

    /**
     * 配置自定义视图转发器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    /**
     * 配置自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加 token 拦截器 (要排除访问或转发时不携带 token 信息的路径)
        registry.addInterceptor(new AccessTokenInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/logout/**", "/unauthorized/**", "/forcedLogout");
    }

    /**
     * 允许跨域访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 拦截路径
                .allowedOrigins("*") // * 代表全部 如果只某个访问地址 http://localhost:7070
                .allowedMethods("GET", "POST","PUT", "DELETE", "HEAD", "OPTIONS") // 允许的请求方式
                .allowCredentials(true)
                .maxAge(3600);
    }
}
