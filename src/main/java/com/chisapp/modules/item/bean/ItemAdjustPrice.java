package com.chisapp.modules.item.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemAdjustPrice implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer cimItemId;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal originalRetailPrice;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal newRetailPrice;

    @Length(max = 50)
    private String explains;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expiryDate;

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

    public Integer getCimItemId() {
        return cimItemId;
    }

    public void setCimItemId(Integer cimItemId) {
        this.cimItemId = cimItemId;
    }

    public BigDecimal getOriginalRetailPrice() {
        return originalRetailPrice;
    }

    public void setOriginalRetailPrice(BigDecimal originalRetailPrice) {
        this.originalRetailPrice = originalRetailPrice;
    }

    public BigDecimal getNewRetailPrice() {
        return newRetailPrice;
    }

    public void setNewRetailPrice(BigDecimal newRetailPrice) {
        this.newRetailPrice = newRetailPrice;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
