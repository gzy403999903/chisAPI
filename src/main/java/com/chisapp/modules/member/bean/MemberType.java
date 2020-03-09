package com.chisapp.modules.member.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class MemberType implements Serializable {
    private Integer id;

    @Length(max = 30)
    @NotBlank
    private String name;

    @Length(max = 30)
    @NotBlank
    private String code;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal depositAmount;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal givenAmount;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal paymentAmount;

    @Min(0)
    @NotNull
    private Integer givenPoints = 1;

    @Digits(integer = 1, fraction = 1)
    @Min(0)
    @NotNull
    private Float discountRate;

    @NotNull
    private Boolean state = true;

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

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(BigDecimal givenAmount) {
        this.givenAmount = givenAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getGivenPoints() {
        return givenPoints;
    }

    public void setGivenPoints(Integer givenPoints) {
        this.givenPoints = givenPoints;
    }

    public Float getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Float discountRate) {
        this.discountRate = discountRate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
