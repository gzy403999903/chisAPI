package com.chisapp.modules.purchase.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseOrder implements Serializable {
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
    private Integer planQuantity;

    @Max(30000)
    @Min(1)
    @NotNull
    private Integer purchaseQuantity;

    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal purchasePrice;

    @NotNull
    private Integer sysClinicId;

    @NotNull
    private Integer pemSupplierId;

    @NotNull
    private Integer pemPurchasePlanId;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    private Integer approverId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date approveDate;

    @NotNull
    private Byte approveState;

    @NotNull
    private Boolean inventoryState;

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

    public Integer getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(Integer planQuantity) {
        this.planQuantity = planQuantity;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getPemSupplierId() {
        return pemSupplierId;
    }

    public void setPemSupplierId(Integer pemSupplierId) {
        this.pemSupplierId = pemSupplierId;
    }

    public Integer getPemPurchasePlanId() {
        return pemPurchasePlanId;
    }

    public void setPemPurchasePlanId(Integer pemPurchasePlanId) {
        this.pemPurchasePlanId = pemPurchasePlanId;
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

    public Boolean getInventoryState() {
        return inventoryState;
    }

    public void setInventoryState(Boolean inventoryState) {
        this.inventoryState = inventoryState;
    }
}
