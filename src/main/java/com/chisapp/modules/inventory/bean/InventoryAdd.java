package com.chisapp.modules.inventory.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InventoryAdd implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer gsmGoodsId;

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal costPrice;

    @Max(99)
    @Min(1)
    @NotNull
    private Byte purchaseTaxRate;

    @Max(99)
    @Min(1)
    @NotNull
    private Byte sellTaxRate;

    @Min(0)
    @Max(30000)
    @NotNull
    private Integer quantity;

    @Length(max = 50)
    @NotBlank
    private String ph;

    @Length(max = 50)
    @NotBlank
    private String pch;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    @NotNull
    private Date producedDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Future
    @NotNull
    private Date expiryDate;

    @NotNull
    private Integer iymInventoryTypeId;

    @Length(max = 50)
    private String billNo;

    @Length(max = 50)
    private String notes;

    @NotNull
    private Integer pemSupplierId;

    private Integer pemPurchaseOrderId;

    @NotNull
    private Integer sysClinicId;

    @NotNull
    private Byte actionType;

    @NotNull
    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date creationDate;

    private Integer approverId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;

    @NotNull
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Byte getPurchaseTaxRate() {
        return purchaseTaxRate;
    }

    public void setPurchaseTaxRate(Byte purchaseTaxRate) {
        this.purchaseTaxRate = purchaseTaxRate;
    }

    public Byte getSellTaxRate() {
        return sellTaxRate;
    }

    public void setSellTaxRate(Byte sellTaxRate) {
        this.sellTaxRate = sellTaxRate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph == null ? null : ph.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public Date getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(Date producedDate) {
        this.producedDate = producedDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getIymInventoryTypeId() {
        return iymInventoryTypeId;
    }

    public void setIymInventoryTypeId(Integer iymInventoryTypeId) {
        this.iymInventoryTypeId = iymInventoryTypeId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Integer getPemSupplierId() {
        return pemSupplierId;
    }

    public void setPemSupplierId(Integer pemSupplierId) {
        this.pemSupplierId = pemSupplierId;
    }

    public Integer getPemPurchaseOrderId() {
        return pemPurchaseOrderId;
    }

    public void setPemPurchaseOrderId(Integer pemPurchaseOrderId) {
        this.pemPurchaseOrderId = pemPurchaseOrderId;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Byte getActionType() {
        return actionType;
    }

    public void setActionType(Byte actionType) {
        this.actionType = actionType;
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
