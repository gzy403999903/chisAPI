package com.chisapp.common.enums;

/**
 * @Author: Tandy
 * @Date: 2019/7/30 8:56
 * @Version 1.0
 */
public enum ApproveStateEnum {

    UNAPPROVED("驳回", (byte)0),
    APPROVED("通过", (byte)1),
    CANCEL("撤销", (byte)2),
    PURCHASING("待采购", (byte)97),
    PRICING("待定价", (byte)98),
    PENDING("待审批", (byte)99);

    private String name;
    private byte index;

    private ApproveStateEnum(String name, byte index) {
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
