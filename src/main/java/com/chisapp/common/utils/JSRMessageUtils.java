package com.chisapp.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 用于处理 JSR 303 验证返回的错误信息
 *
 * @Author: Tandy
 * @Date: 2019/5/8 9:45
 * @Version 1.0
 */
public class JSRMessageUtils {

    /**
     * 获取违反约束的首个字段信息
     * @param result
     * @return
     */
    public static synchronized String getFirstMsg(BindingResult result) {
        List<FieldError> fieldErrors = result.getFieldErrors();
        FieldError field = fieldErrors.get(0);
        String fieldName = field.getField();
        String errorMessage = field.getDefaultMessage();
        return "[" + fieldName + "] : " + errorMessage;
    }

}
