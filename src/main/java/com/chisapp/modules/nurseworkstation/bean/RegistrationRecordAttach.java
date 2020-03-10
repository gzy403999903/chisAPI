package com.chisapp.modules.nurseworkstation.bean;

/**
 * @Author: Tandy
 * @Date: 2019/12/16 16:27
 * @Version 1.0
 */
public class RegistrationRecordAttach {
    /** 会员属性 *******************************************************************************************************/
    private String mrmMemberOid;

    private String mrmMemberName;

    private String genderName;

    private Integer age;

    private String phone;

    private Float memberDiscountRate;

    /** 医生属性 *******************************************************************************************************/
    private String sysDoctorName;

    private String sysClinicRoomName;

    /** 其他属性 *******************************************************************************************************/
    private String creatorName;

    private Boolean receptionState = false;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSysDoctorName() {
        return sysDoctorName;
    }

    public Float getMemberDiscountRate() {
        return memberDiscountRate;
    }

    public void setMemberDiscountRate(Float memberDiscountRate) {
        this.memberDiscountRate = memberDiscountRate;
    }

    public void setSysDoctorName(String sysDoctorName) {
        this.sysDoctorName = sysDoctorName;
    }

    public String getSysClinicRoomName() {
        return sysClinicRoomName;
    }

    public void setSysClinicRoomName(String sysClinicRoomName) {
        this.sysClinicRoomName = sysClinicRoomName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Boolean getReceptionState() {
        return receptionState;
    }

    public void setReceptionState(Boolean receptionState) {
        this.receptionState = receptionState;
    }
}
