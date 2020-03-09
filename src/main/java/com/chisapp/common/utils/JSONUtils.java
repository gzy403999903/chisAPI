package com.chisapp.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON 工具类
 *
 * @Author: Tandy
 * @Date: 2019/5/19 16:31
 * @Version 1.0
 */
public class JSONUtils {

    /**
     * 将 对象 转成 JSON
     *
     * @param obj
     * @return
     */
    public synchronized static String parseObjectToJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("对象转JSON错误");
        }
    }

    /**
     * 解析 JSON 到指定的类型
     * 例: (json, new TypeReference<Map<String, String>>() {}) 将转成 Map 类型
     * @param json 传入的 JSON 字符串
     * @param jsonTypeReference 要转成的类型
     * @param <T>
     * @return
     */
    public synchronized static <T> T parseJsonToObject(String json, TypeReference<T> jsonTypeReference) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return (T) mapper.readValue(json, jsonTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON转对象错误");
        }
    }

}
