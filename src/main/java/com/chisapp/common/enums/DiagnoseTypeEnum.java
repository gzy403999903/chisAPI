package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/11/6 16:46
 * @Version 1.0
 */
public enum DiagnoseTypeEnum {

    WESTERN_DIAGNOSE("西医诊断", 1),
    CHINESE_DIAGNOSE("中医诊断", 2);

    private String name;
    private Integer index;

    private DiagnoseTypeEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
