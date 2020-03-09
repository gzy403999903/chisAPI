package com.chisapp.common.component;

import java.util.HashMap;
import java.util.Map;

/**
 * 包装类 用于返回给前端的数据模型
 */
public class PageResult {
    private Integer code;
    private String msg;
    private Map<String, Object> resultSet = new HashMap<String, Object>();

    public static PageResult success() {
        PageResult pageResult = new PageResult();
        if (pageResult.getCode() == null) {
            pageResult.setCode(200);
        }
        if (pageResult.getMsg() == null) {
            pageResult.setMsg("操作成功");
        }
        return pageResult;
    }

    public static PageResult fail() {
        PageResult pageResult = new PageResult();
        if (pageResult.getCode() == null) {
            pageResult.setCode(400);
        }
        if (pageResult.getMsg() == null) {
            pageResult.setMsg("操作失败");
        }
        return pageResult;
    }

    public PageResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    public PageResult msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public PageResult resultSet(String key, Object value) {
        this.getResultSet().put(key, value);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Map<String, Object> resultSet) {
        this.resultSet = resultSet;
    }

}
