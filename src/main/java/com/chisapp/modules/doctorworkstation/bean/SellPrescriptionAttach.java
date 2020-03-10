package com.chisapp.modules.doctorworkstation.bean;

import java.math.BigDecimal;

/**
 * @Author: Tandy
 * @Date: 2019/12/9 10:28
 * @Version 1.0
 */
public class SellPrescriptionAttach {

    /** 商品属性 *******************************************************************************************************/
    private String oid;

    private BigDecimal retailPrice;

    private Integer inventoryQuantity;

    private Boolean discountable;

    /** 会员属性 *******************************************************************************************************/
    private String mrmMemberOid;

    private String mrmMemberName;

    private String genderName;

    private String phone;

    private Float memberDiscountRate;

    /** 其他属性 *******************************************************************************************************/
    private Integer onceContainQuantity;

    private String sysDoctorName;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Boolean getDiscountable() {
        return discountable;
    }

    public void setDiscountable(Boolean discountable) {
        this.discountable = discountable;
    }

    public String getMrmMemberOid() {
        return mrmMemberOid;
    }

    public void setMrmMemberOid(String mrmMemberOid) {
        this.mrmMemberOid = mrmMemberOid;
    }

    public String getMrmMemberName() {
        return mrmMemberName;
    }

    public void setMrmMemberName(String mrmMemberName) {
        this.mrmMemberName = mrmMemberName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getMemberDiscountRate() {
        return memberDiscountRate;
    }

    public void setMemberDiscountRate(Float memberDiscountRate) {
        this.memberDiscountRate = memberDiscountRate;
    }

    public Integer getOnceContainQuantity() {
        return onceContainQuantity;
    }

    public void setOnceContainQuantity(Integer onceContainQuantity) {
        this.onceContainQuantity = onceContainQuantity;
    }

    public String getSysDoctorName() {
        return sysDoctorName;
    }

    public void setSysDoctorName(String sysDoctorName) {
        this.sysDoctorName = sysDoctorName;
    }
}
