package com.chisapp.modules.inventory.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SelfUsedRecord implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer iymInventoryId;

    @NotNull
    private Integer gsmGoodsId;

    @Length(max = 50)
    @NotBlank
    private String ph;

    @Length(max = 50)
    @NotBlank
    private String pch;

    @Min(1)
    @NotNull
    private Integer splitQuantity = 1;

    @Min(0)
    @Max(30000)
    @NotNull
    private Integer quantity;

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal costPrice;

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal averageCostPrice;

    @Max(99)
    @Min(1)
    @NotNull
    private Byte purchaseTaxRate;

    @Max(99)
    @Min(1)
    @NotNull
    private Byte sellTaxRate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    @NotNull
    private Date producedDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Future
    @NotNull
    private Date expiryDate;

    @NotNull
    private Integer pemSupplierId;

    @NotNull
    private Integer iymInventoryAddId;

    @Length(max = 50)
    private String notes;

    private Integer sysClinicId;

    private Integer creatorId;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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

    public Integer getIymInventoryId() {
        return iymInventoryId;
    }

    public void setIymInventoryId(Integer iymInventoryId) {
        this.iymInventoryId = iymInventoryId;
    }

    public Integer getGsmGoodsId() {
        return gsmGoodsId;
    }

    public void setGsmGoodsId(Integer gsmGoodsId) {
        this.gsmGoodsId = gsmGoodsId;
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

    public Integer getSplitQuantity() {
        return splitQuantity;
    }

    public void setSplitQuantity(Integer splitQuantity) {
        this.splitQuantity = splitQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getAverageCostPrice() {
        return averageCostPrice;
    }

    public void setAverageCostPrice(BigDecimal averageCostPrice) {
        this.averageCostPrice = averageCostPrice;
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

    public Integer getPemSupplierId() {
        return pemSupplierId;
    }

    public void setPemSupplierId(Integer pemSupplierId) {
        this.pemSupplierId = pemSupplierId;
    }

    public Integer getIymInventoryAddId() {
        return iymInventoryAddId;
    }

    public void setIymInventoryAddId(Integer iymInventoryAddId) {
        this.iymInventoryAddId = iymInventoryAddId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
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
