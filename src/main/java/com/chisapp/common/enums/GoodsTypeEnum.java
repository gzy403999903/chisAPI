package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/8/19 16:28
 * @Version 1.0
 */
public enum GoodsTypeEnum {

    WESTERN_DRUGS("西药", 101),
    CHINESE_DRUGS("中药", 102),
    HYGIENIC_MATERIAL("卫生材料", 103);

    private String name;
    private Integer index;

    private GoodsTypeEnum(String name, Integer index) {
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
