package com.chisapp.modules.goods.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsAdjustPrice implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer gsmGoodsId;

    @Digits(integer = 8, fraction = 4)
    @Min(0)
    @NotNull
    private BigDecimal originalRetailPrice;

    @Digits(integer = 8, fraction = 4)
    @Min(0)
    @NotNull
    private BigDecimal newRetailPrice;

    @Digits(integer = 8, fraction = 4)
    @Min(0)
    @NotNull
    private BigDecimal originalSplitPrice;

    @Digits(integer = 8, fraction = 4)
    @Min(0)
    @NotNull
    private BigDecimal newSplitPrice;

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

    public Integer getGsmGoodsId() {
        return gsmGoodsId;
    }

    public void setGsmGoodsId(Integer gsmGoodsId) {
        this.gsmGoodsId = gsmGoodsId;
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

    public BigDecimal getOriginalSplitPrice() {
        return originalSplitPrice;
    }

    public void setOriginalSplitPrice(BigDecimal originalSplitPrice) {
        this.originalSplitPrice = originalSplitPrice;
    }

    public BigDecimal getNewSplitPrice() {
        return newSplitPrice;
    }

    public void setNewSplitPrice(BigDecimal newSplitPrice) {
        this.newSplitPrice = newSplitPrice;
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
