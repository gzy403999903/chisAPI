package com.chisapp.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * @Author: Tandy
 * @Date: 2020/2/29 20:01
 * @Version 1.0
 */
public class HttpUtils {

    /**
     * 获取配置信息
     * @return
     */
    private static RequestConfig getRequestConfig () {
        return RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)  // 设置请求超时时间(单位毫秒)
                .setSocketTimeout(5000) // socket读写超时时间(单位毫秒)
                .setRedirectsEnabled(false) // 设置是否允许重定向(默认为true)
                .build();
    }

    /**
     * 有参 Get 方法
     * @param uri
     * @return
     */
    public synchronized static String doGet (URI uri) throws Exception {
        String data = null;

        // 获取 Http 对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建 Get 请求
        HttpGet httpGet = new HttpGet(uri);
        // 设置请求参数
        httpGet.setConfig(getRequestConfig());
        // 响应模型
        CloseableHttpResponse response = null;

        // 以下操作虽然异常都抛出去了, 但是必须保证在有异常的情况下也要执行 finally 内容
        try {
            // 发送请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                data = EntityUtils.toString(responseEntity);
            }
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        }

        return data;
    }


}
