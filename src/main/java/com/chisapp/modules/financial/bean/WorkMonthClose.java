package com.chisapp.modules.financial.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorkMonthClose implements Serializable {
    private Integer id;

    @NotNull
    private Integer apYear;

    @NotNull
    private Integer apMonth;

    @NotNull
    private Integer sysClinicId;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsQccb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsQccb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsCgcb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsCgcb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsThcb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsThcb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsXscb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsXscb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsLycb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsLycb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsBscb = new BigDecimal("0");
    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsBscb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal hsQmcb = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wsQmcb = new BigDecimal("0");

    private Integer operatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operateDate;

    @NotNull
    private Boolean closeState = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApYear() {
        return apYear;
    }

    public void setApYear(Integer apYear) {
        this.apYear = apYear;
    }

    public Integer getApMonth() {
        return apMonth;
    }

    public void setApMonth(Integer apMonth) {
        this.apMonth = apMonth;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public BigDecimal getHsQccb() {
        return hsQccb;
    }

    public void setHsQccb(BigDecimal hsQccb) {
        this.hsQccb = hsQccb;
    }

    public BigDecimal getWsQccb() {
        return wsQccb;
    }

    public void setWsQccb(BigDecimal wsQccb) {
        this.wsQccb = wsQccb;
    }

    public BigDecimal getHsCgcb() {
        return hsCgcb;
    }

    public void setHsCgcb(BigDecimal hsCgcb) {
        this.hsCgcb = hsCgcb;
    }

    public BigDecimal getWsCgcb() {
        return wsCgcb;
    }

    public void setWsCgcb(BigDecimal wsCgcb) {
        this.wsCgcb = wsCgcb;
    }

    public BigDecimal getHsThcb() {
        return hsThcb;
    }

    public void setHsThcb(BigDecimal hsThcb) {
        this.hsThcb = hsThcb;
    }

    public BigDecimal getWsThcb() {
        return wsThcb;
    }

    public void setWsThcb(BigDecimal wsThcb) {
        this.wsThcb = wsThcb;
    }

    public BigDecimal getHsXscb() {
        return hsXscb;
    }

    public void setHsXscb(BigDecimal hsXscb) {
        this.hsXscb = hsXscb;
    }

    public BigDecimal getWsXscb() {
        return wsXscb;
    }

    public void setWsXscb(BigDecimal wsXscb) {
        this.wsXscb = wsXscb;
    }

    public BigDecimal getHsLycb() {
        return hsLycb;
    }

    public void setHsLycb(BigDecimal hsLycb) {
        this.hsLycb = hsLycb;
    }

    public BigDecimal getWsLycb() {
        return wsLycb;
    }

    public void setWsLycb(BigDecimal wsLycb) {
        this.wsLycb = wsLycb;
    }

    public BigDecimal getHsBscb() {
        return hsBscb;
    }

    public void setHsBscb(BigDecimal hsBscb) {
        this.hsBscb = hsBscb;
    }

    public BigDecimal getWsBscb() {
        return wsBscb;
    }

    public void setWsBscb(BigDecimal wsBscb) {
        this.wsBscb = wsBscb;
    }

    public BigDecimal getHsQmcb() {
        return hsQmcb;
    }

    public void setHsQmcb(BigDecimal hsQmcb) {
        this.hsQmcb = hsQmcb;
    }

    public BigDecimal getWsQmcb() {
        return wsQmcb;
    }

    public void setWsQmcb(BigDecimal wsQmcb) {
        this.wsQmcb = wsQmcb;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Boolean getCloseState() {
        return closeState;
    }

    public void setCloseState(Boolean closeState) {
        this.closeState = closeState;
    }
}
