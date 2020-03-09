package com.chisapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.chisapp.modules.*.dao") // 指定 mybatis 接口文件所在的包
@EnableCaching // 开启缓存
@EnableTransactionManagement // 开启事务
@SpringBootApplication
public class ChisApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChisApiApplication.class, args);
    }

}
