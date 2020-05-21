package com.chisapp.modules.financial.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorkGroupClose implements Serializable {
    private Integer id;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date logicDate;

    private Integer sysClinicId;

    private Integer flmWorkGroupId;

    private Integer operatorId;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal cash = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal unionpay = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wechatpay = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal alipay = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal cmedicare = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal pmedicare = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal memberBalance = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal creditpay = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal coupon = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal otherAmount = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal actualAmount = new BigDecimal("0");

    private Integer lastUpdaterId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    private Boolean closeState = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLogicDate() {
        return logicDate;
    }

    public void setLogicDate(Date logicDate) {
        this.logicDate = logicDate;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getFlmWorkGroupId() {
        return flmWorkGroupId;
    }

    public void setFlmWorkGroupId(Integer flmWorkGroupId) {
        this.flmWorkGroupId = flmWorkGroupId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(BigDecimal unionpay) {
        this.unionpay = unionpay;
    }

    public BigDecimal getWechatpay() {
        return wechatpay;
    }

    public void setWechatpay(BigDecimal wechatpay) {
        this.wechatpay = wechatpay;
    }

    public BigDecimal getAlipay() {
        return alipay;
    }

    public void setAlipay(BigDecimal alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getCmedicare() {
        return cmedicare;
    }

    public void setCmedicare(BigDecimal cmedicare) {
        this.cmedicare = cmedicare;
    }

    public BigDecimal getPmedicare() {
        return pmedicare;
    }

    public void setPmedicare(BigDecimal pmedicare) {
        this.pmedicare = pmedicare;
    }

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    public BigDecimal getCreditpay() {
        return creditpay;
    }

    public void setCreditpay(BigDecimal creditpay) {
        this.creditpay = creditpay;
    }

    public BigDecimal getCoupon() {
        return coupon;
    }

    public void setCoupon(BigDecimal coupon) {
        this.coupon = coupon;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getLastUpdaterId() {
        return lastUpdaterId;
    }

    public void setLastUpdaterId(Integer lastUpdaterId) {
        this.lastUpdaterId = lastUpdaterId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getCloseState() {
        return closeState;
    }

    public void setCloseState(Boolean closeState) {
        this.closeState = closeState;
    }
}
