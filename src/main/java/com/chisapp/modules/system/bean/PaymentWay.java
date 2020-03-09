package com.chisapp.modules.system.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PaymentWay implements Serializable {
    private Integer id;

    @Length(max = 20)
    @NotBlank
    private String name;

    @Length(max = 20)
    @NotBlank
    private String code;

    @NotNull
    private Boolean cashFlag = false;

    @NotNull
    private Boolean balanceFlag = false;

    @NotNull
    private Boolean couponFlag = false;

    @NotNull
    private Boolean state = false;

    @NotNull
    private Boolean required = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Boolean getCashFlag() {
        return cashFlag;
    }

    public void setCashFlag(Boolean cashFlag) {
        this.cashFlag = cashFlag;
    }

    public Boolean getBalanceFlag() {
        return balanceFlag;
    }

    public void setBalanceFlag(Boolean balanceFlag) {
        this.balanceFlag = balanceFlag;
    }

    public Boolean getCouponFlag() {
        return couponFlag;
    }

    public void setCouponFlag(Boolean couponFlag) {
        this.couponFlag = couponFlag;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
