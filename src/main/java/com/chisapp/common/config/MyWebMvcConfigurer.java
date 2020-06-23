package com.chisapp.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 为文件创建虚拟路径
 * @Author: Tandy
 * @Date: 2020-06-14 21:09
 * @Version 1.0
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 上传文件虚拟路径
    @Value("${upload.virtual-dir}**")
    private String virtualDir;

    // 上传图片的真实路径
    @Value("${upload.image-dir}")
    private String imageDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取操作系统
        String os = System.getProperty("os.name").toLowerCase();
        logger.info("服务器系统类型为: " + os);
        if (os.contains("windows") || os.contains("mac")) {
            imageDir = "file:" + imageDir;
        }

        registry.addResourceHandler(this.virtualDir).addResourceLocations(this.imageDir);
    }
}
