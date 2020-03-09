package com.chisapp.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid 配置文件
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:26
 * @Version 1.0
 */
@Configuration
public class DruidConfig {

    /***
     * 配置使用自定义数据源配置
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 配置用于访问后台的用户名和密码
     * 在配置文件读取
     */
    @Value("${druid.username}")
    private String username;
    @Value("${druid.password}")
    private String password;

    /**
     * 配置一个 Servlet 加入 SpringBoot 容器
     * 用于用户访问 Druid 后台进行管理, 配置了以下参数
     * loginUsername 访问用户名
     * loginPassword 访问密码
     * allow 允许访问的地址
     * deny 拒绝访问地址
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername",username);
        initParams.put("loginPassword",password);
        //initParams.put("allow","localhost");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置一个 Filter 加入 SpringBoot 容器
     * 用于拦截请求 /* 默认拦截所有请求，配置了以下参数
     * exclusions 不进行拦截的请求
     * @return
     */
    @Bean
    public FilterRegistrationBean webViewFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
