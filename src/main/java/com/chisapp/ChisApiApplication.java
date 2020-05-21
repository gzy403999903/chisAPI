package com.chisapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy(exposeProxy = true) // 开启暴露AOP代理对象
@EnableScheduling // 开启基于注解的定时任务
@MapperScan("com.chisapp.modules.*.dao") // 指定 mybatis 接口文件所在的包
@EnableCaching // 开启缓存
@EnableTransactionManagement // 开启事务
@SpringBootApplication
public class ChisApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChisApiApplication.class, args);
    }

}
