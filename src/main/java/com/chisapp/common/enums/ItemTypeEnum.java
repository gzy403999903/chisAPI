package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/7/30 11:28
 * @Version 1.0
 */
public enum ItemTypeEnum {

    MEDICAL_ITEM("医技项目", 101),
    ADJUVANT_ITEM("辅助项目", 102),
    OTHER_ITEM("其他项目", 103),
    ;

    private String name;
    private Integer index;

    private ItemTypeEnum(String name, Integer index) {
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
