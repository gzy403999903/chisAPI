package com.chisapp.modules.purchase.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PurchasePlan implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer gsmGoodsId;

    @Max(30000)
    @Min(1)
    @NotNull
    private Integer quantity;

    @Max(30000)
    @Min(0)
    @NotNull
    private Integer recommendQuantity = 0;

    @Max(366)
    @Min(0)
    @NotNull
    private Byte planDays;

    @NotNull
    private Integer sysClinicId;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    private Integer approverId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date approveDate;

    private Byte approveState;

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

    public String getMxh() {
        return mxh;
    }

    public void setMxh(String mxh) {
        this.mxh = mxh == null ? null : mxh.trim();
    }

    public Integer getGsmGoodsId() {
        return gsmGoodsId;
    }

    public void setGsmGoodsId(Integer gsmGoodsId) {
        this.gsmGoodsId = gsmGoodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRecommendQuantity() {
        return recommendQuantity;
    }

    public void setRecommendQuantity(Integer recommendQuantity) {
        this.recommendQuantity = recommendQuantity;
    }

    public Byte getPlanDays() {
        return planDays;
    }

    public void setPlanDays(Byte planDays) {
        this.planDays = planDays;
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

    public Integer getApproverId() {
        return approverId;
    }

    public void setApproverId(Integer approverId) {
        this.approverId = approverId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Byte getApproveState() {
        return approveState;
    }

    public void setApproveState(Byte approveState) {
        this.approveState = approveState;
    }
}
