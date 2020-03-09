package com.chisapp.modules.nurseworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentRecord implements Serializable {
    private Integer id;

    @Length(max = 50)
    private String lsh;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal cash;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal memberBalance;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal unionpay;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal alipay;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal wechatpay;

    private Integer sysPaymentWayId;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal sysPaymentWayAmount;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal actualAmount;

    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal disparityAmount;

    private Integer sysClinicId;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    /*** 不保存 运算需求的字段 ******************************************************************************************/
    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal cashBackAmount;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh == null ? null : lsh.trim();
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    public BigDecimal getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(BigDecimal unionpay) {
        this.unionpay = unionpay;
    }

    public BigDecimal getAlipay() {
        return alipay;
    }

    public void setAlipay(BigDecimal alipay) {
        this.alipay = alipay;
    }

    public BigDecimal getWechatpay() {
        return wechatpay;
    }

    public void setWechatpay(BigDecimal wechatpay) {
        this.wechatpay = wechatpay;
    }

    public Integer getSysPaymentWayId() {
        return sysPaymentWayId;
    }

    public void setSysPaymentWayId(Integer sysPaymentWayId) {
        this.sysPaymentWayId = sysPaymentWayId;
    }

    public BigDecimal getSysPaymentWayAmount() {
        return sysPaymentWayAmount;
    }

    public void setSysPaymentWayAmount(BigDecimal sysPaymentWayAmount) {
        this.sysPaymentWayAmount = sysPaymentWayAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getDisparityAmount() {
        return disparityAmount;
    }

    public void setDisparityAmount(BigDecimal disparityAmount) {
        this.disparityAmount = disparityAmount;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
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

    public BigDecimal getCashBackAmount() {
        return cashBackAmount;
    }

    public void setCashBackAmount(BigDecimal cashBackAmount) {
        this.cashBackAmount = cashBackAmount;
    }
}
