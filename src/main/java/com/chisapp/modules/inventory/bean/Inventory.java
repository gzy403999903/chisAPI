package com.chisapp.modules.inventory.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Inventory extends InventoryAttach implements Serializable {
    private Integer id;

    @NotNull
    private Integer sysClinicId;

    @NotNull
    private Integer iymInventoryTypeId;

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

    @Max(30000)
    @Min(0)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getIymInventoryTypeId() {
        return iymInventoryTypeId;
    }

    public void setIymInventoryTypeId(Integer iymInventoryTypeId) {
        this.iymInventoryTypeId = iymInventoryTypeId;
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
}
