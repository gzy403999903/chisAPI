package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/11/3 19:53
 * @Version 1.0
 */
public enum WaitStateEnum {

    WAITING("候诊中", (byte)0),
    DIAGNOSING("就诊中", (byte)1),
    DIAGNOSED("已就诊", (byte)2);

    private String name;
    private byte index;

    private WaitStateEnum(String name, byte index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }
}
