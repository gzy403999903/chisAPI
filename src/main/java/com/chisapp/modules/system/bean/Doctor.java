package com.chisapp.modules.system.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Doctor implements Serializable {
    private Integer id;

    private Integer mainSysClinicId;

    private Integer subSysClinicId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date subSysClinicExpiryDate;

    @NotNull
    private Integer sysClinicRoomId;

    private Integer doctorTitlesId;

    private Integer practiceTypeId;

    private Integer sysPracticeScopeId;

    @Length(max = 200)
    private String intro;

    @Length(max = 200)
    private String avatarUrl;

    @Length(max = 200)
    private String signatureUrl;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMainSysClinicId() {
        return mainSysClinicId;
    }

    public void setMainSysClinicId(Integer mainSysClinicId) {
        this.mainSysClinicId = mainSysClinicId;
    }

    public Integer getSubSysClinicId() {
        return subSysClinicId;
    }

    public void setSubSysClinicId(Integer subSysClinicId) {
        this.subSysClinicId = subSysClinicId;
    }

    public Date getSubSysClinicExpiryDate() {
        return subSysClinicExpiryDate;
    }

    public void setSubSysClinicExpiryDate(Date subSysClinicExpiryDate) {
        this.subSysClinicExpiryDate = subSysClinicExpiryDate;
    }

    public Integer getSysClinicRoomId() {
        return sysClinicRoomId;
    }

    public void setSysClinicRoomId(Integer sysClinicRoomId) {
        this.sysClinicRoomId = sysClinicRoomId;
    }

    public Integer getDoctorTitlesId() {
        return doctorTitlesId;
    }

    public void setDoctorTitlesId(Integer doctorTitlesId) {
        this.doctorTitlesId = doctorTitlesId;
    }

    public Integer getPracticeTypeId() {
        return practiceTypeId;
    }

    public void setPracticeTypeId(Integer practiceTypeId) {
        this.practiceTypeId = practiceTypeId;
    }

    public Integer getSysPracticeScopeId() {
        return sysPracticeScopeId;
    }

    public void setSysPracticeScopeId(Integer sysPracticeScopeId) {
        this.sysPracticeScopeId = sysPracticeScopeId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl == null ? null : signatureUrl.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
