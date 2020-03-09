package com.chisapp.modules.system.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Doctor implements Serializable {
    private Integer id;

    @NotNull
    private Integer sysClinicRoomId;

    @DateTimeFormat(pattern="HH:mm")
    private Date monBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date monEndTime;

    @Max(500)
    @NotNull
    private Short monNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date tueBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date tueEndTime;

    @Max(500)
    @NotNull
    private Short tueNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date wedBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date wedEndTime;

    @Max(500)
    @NotNull
    private Short wedNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date thuBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date thuEndTime;

    @Max(500)
    @NotNull
    private Short thuNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date friBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date friEndTime;

    @Max(500)
    @NotNull
    private Short friNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date satBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date satEndTime;

    @Max(500)
    @NotNull
    private Short satNo;

    @DateTimeFormat(pattern="HH:mm")
    private Date sunBeginTime;

    @DateTimeFormat(pattern="HH:mm")
    private Date sunEndTime;

    @Max(500)
    @NotNull
    private Short sunNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysClinicRoomId() {
        return sysClinicRoomId;
    }

    public void setSysClinicRoomId(Integer sysClinicRoomId) {
        this.sysClinicRoomId = sysClinicRoomId;
    }

    public Date getMonBeginTime() {
        return monBeginTime;
    }

    public void setMonBeginTime(Date monBeginTime) {
        this.monBeginTime = monBeginTime;
    }

    public Date getMonEndTime() {
        return monEndTime;
    }

    public void setMonEndTime(Date monEndTime) {
        this.monEndTime = monEndTime;
    }

    public Short getMonNo() {
        return monNo;
    }

    public void setMonNo(Short monNo) {
        this.monNo = monNo;
    }

    public Date getTueBeginTime() {
        return tueBeginTime;
    }

    public void setTueBeginTime(Date tueBeginTime) {
        this.tueBeginTime = tueBeginTime;
    }

    public Date getTueEndTime() {
        return tueEndTime;
    }

    public void setTueEndTime(Date tueEndTime) {
        this.tueEndTime = tueEndTime;
    }

    public Short getTueNo() {
        return tueNo;
    }

    public void setTueNo(Short tueNo) {
        this.tueNo = tueNo;
    }

    public Date getWedBeginTime() {
        return wedBeginTime;
    }

    public void setWedBeginTime(Date wedBeginTime) {
        this.wedBeginTime = wedBeginTime;
    }

    public Date getWedEndTime() {
        return wedEndTime;
    }

    public void setWedEndTime(Date wedEndTime) {
        this.wedEndTime = wedEndTime;
    }

    public Short getWedNo() {
        return wedNo;
    }

    public void setWedNo(Short wedNo) {
        this.wedNo = wedNo;
    }

    public Date getThuBeginTime() {
        return thuBeginTime;
    }

    public void setThuBeginTime(Date thuBeginTime) {
        this.thuBeginTime = thuBeginTime;
    }

    public Date getThuEndTime() {
        return thuEndTime;
    }

    public void setThuEndTime(Date thuEndTime) {
        this.thuEndTime = thuEndTime;
    }

    public Short getThuNo() {
        return thuNo;
    }

    public void setThuNo(Short thuNo) {
        this.thuNo = thuNo;
    }

    public Date getFriBeginTime() {
        return friBeginTime;
    }

    public void setFriBeginTime(Date friBeginTime) {
        this.friBeginTime = friBeginTime;
    }

    public Date getFriEndTime() {
        return friEndTime;
    }

    public void setFriEndTime(Date friEndTime) {
        this.friEndTime = friEndTime;
    }

    public Short getFriNo() {
        return friNo;
    }

    public void setFriNo(Short friNo) {
        this.friNo = friNo;
    }

    public Date getSatBeginTime() {
        return satBeginTime;
    }

    public void setSatBeginTime(Date satBeginTime) {
        this.satBeginTime = satBeginTime;
    }

    public Date getSatEndTime() {
        return satEndTime;
    }

    public void setSatEndTime(Date satEndTime) {
        this.satEndTime = satEndTime;
    }

    public Short getSatNo() {
        return satNo;
    }

    public void setSatNo(Short satNo) {
        this.satNo = satNo;
    }

    public Date getSunBeginTime() {
        return sunBeginTime;
    }

    public void setSunBeginTime(Date sunBeginTime) {
        this.sunBeginTime = sunBeginTime;
    }

    public Date getSunEndTime() {
        return sunEndTime;
    }

    public void setSunEndTime(Date sunEndTime) {
        this.sunEndTime = sunEndTime;
    }

    public Short getSunNo() {
        return sunNo;
    }

    public void setSunNo(Short sunNo) {
        this.sunNo = sunNo;
    }
}
