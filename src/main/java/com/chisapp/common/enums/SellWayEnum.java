package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/12/10 10:05
 * @Version 1.0
 */
public enum SellWayEnum {

    PRESCRIPTION("处方销售", 1),
    POS("POS销售", 2);

    private String name;
    private Integer index;

    private SellWayEnum(String name, Integer index) {
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
