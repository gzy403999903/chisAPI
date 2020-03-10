package com.chisapp.modules.datareport.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: Tandy
 * @Date: 2019/12/11 18:14
 * @Version 1.0
 */
public class SellRecordAttach {
    /** 商品属性 *******************************************************************************************************/
    private String oid;

    private String name;

    private String specs;

    private String unitsName;

    /** 会员属性 *******************************************************************************************************/
    private String mrmMemberOid;

    private String mrmMemberName;

    private String genderName;

    private String phone;

    private Float memberDiscountRate;

    /** 其他属性 *******************************************************************************************************/
    private Integer onceContainQuantity;

    private Boolean registrationFeeFlag = false;

    private String sysDoctorName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dwtSellPrescriptionDate;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
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

    public Boolean getRegistrationFeeFlag() {
        return registrationFeeFlag;
    }

    public void setRegistrationFeeFlag(Boolean registrationFeeFlag) {
        this.registrationFeeFlag = registrationFeeFlag;
    }

    public String getSysDoctorName() {
        return sysDoctorName;
    }

    public void setSysDoctorName(String sysDoctorName) {
        this.sysDoctorName = sysDoctorName;
    }

    public Date getDwtSellPrescriptionDate() {
        return dwtSellPrescriptionDate;
    }

    public void setDwtSellPrescriptionDate(Date dwtSellPrescriptionDate) {
        this.dwtSellPrescriptionDate = dwtSellPrescriptionDate;
    }
}
