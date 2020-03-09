package com.chisapp.modules.goods.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Goods implements Serializable {
    private Integer id;

    private Integer gsmGoodsTypeId;

    @NotBlank
    @Length(max = 50)
    private String oid;

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotBlank
    @Length(max = 50)
    private String code;

    @Length(max = 50)
    private String spName;

    @Length(max = 50)
    private String spCode;

    @NotBlank
    @Length(max = 50)
    private String specs;

    @NotNull
    private Integer goodsUnitsId;

    @NotNull
    private Integer goodsClassifyId;

    private Integer sysSecondClassifyId;

    @Length(max = 20)
    private String barcode;

    @NotNull
    private Integer manufacturerId;

    @NotNull
    private Integer originId;

    @NotNull
    private Integer billingTypeId;

    @Digits(integer = 6, fraction = 4)
    @Min(0)
    @NotNull
    private BigDecimal retailPrice;

    @Max(99)
    @Min(0)
    @NotNull
    private Byte profitCommissionRate;

    @Max(99)
    @Min(0)
    @NotNull
    private Byte purchaseTaxRate;

    @Max(99)
    @Min(0)
    @NotNull
    private Byte sellTaxRate;

    @Max(30000)
    @Min(1)
    @NotNull
    private Integer minPurchaseQuantity = 1;

    private Integer sellClassifyId;

    @NotNull
    private Boolean discountable;

    @NotNull
    private Boolean returnable;

    @NotNull
    private Boolean freeGoods;

    @NotNull
    private Boolean lossable;

    @NotNull
    private Boolean state;

    @NotNull
    private Boolean splitable = false;

    @Max(30000) // 此处数据类型应和库存数量一致  全部使用 Integer 第二个版本更正
    @Min(1)
    private Integer splitQuantity = 1;

    private Integer splitUnitsId;

    @Digits(integer = 6, fraction = 4)
    @Min(0)
    private BigDecimal splitPrice;

    private Integer doseTypeId; // 剂型

    @Digits(integer = 6, fraction = 4)
    private BigDecimal dose; // 剂量

    private Integer doseUnitsId; // 剂量单位

    private Integer specialDrugId;

    private Integer drugUsageId;

    @NotNull
    private Boolean odored;

    private Integer storageTemperatureId;

    private Short conservationDays;

    @NotNull
    private Boolean prescription;

    private Integer prescriptionTypeId;

    @NotNull
    private Boolean ybDrug;

    @Length(max = 50)
    private String ybOid;

    @Digits(integer = 6, fraction = 4)
    @Min(0)
    private BigDecimal ybPrice;

    @Length(max = 50)
    private String approvalNum;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date approvalNumExpiryDate;

    private Integer creatorId;

    // @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    private Integer lastUpdaterId;

    // @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGsmGoodsTypeId() {
        return gsmGoodsTypeId;
    }

    public void setGsmGoodsTypeId(Integer gsmGoodsTypeId) {
        this.gsmGoodsTypeId = gsmGoodsTypeId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
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

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs == null ? null : specs.trim();
    }

    public Integer getGoodsUnitsId() {
        return goodsUnitsId;
    }

    public void setGoodsUnitsId(Integer goodsUnitsId) {
        this.goodsUnitsId = goodsUnitsId;
    }

    public Integer getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(Integer goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public Integer getSysSecondClassifyId() {
        return sysSecondClassifyId;
    }

    public void setSysSecondClassifyId(Integer sysSecondClassifyId) {
        this.sysSecondClassifyId = sysSecondClassifyId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getBillingTypeId() {
        return billingTypeId;
    }

    public void setBillingTypeId(Integer billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Byte getProfitCommissionRate() {
        return profitCommissionRate;
    }

    public void setProfitCommissionRate(Byte profitCommissionRate) {
        this.profitCommissionRate = profitCommissionRate;
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

    public Integer getMinPurchaseQuantity() {
        return minPurchaseQuantity;
    }

    public void setMinPurchaseQuantity(Integer minPurchaseQuantity) {
        this.minPurchaseQuantity = minPurchaseQuantity;
    }

    public Integer getSellClassifyId() {
        return sellClassifyId;
    }

    public void setSellClassifyId(Integer sellClassifyId) {
        this.sellClassifyId = sellClassifyId;
    }

    public Boolean getDiscountable() {
        return discountable;
    }

    public void setDiscountable(Boolean discountable) {
        this.discountable = discountable;
    }

    public Boolean getReturnable() {
        return returnable;
    }

    public void setReturnable(Boolean returnable) {
        this.returnable = returnable;
    }

    public Boolean getFreeGoods() {
        return freeGoods;
    }

    public void setFreeGoods(Boolean freeGoods) {
        this.freeGoods = freeGoods;
    }

    public Boolean getLossable() {
        return lossable;
    }

    public void setLossable(Boolean lossable) {
        this.lossable = lossable;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getSplitable() {
        return splitable;
    }

    public void setSplitable(Boolean splitable) {
        this.splitable = splitable;
    }

    public Integer getSplitQuantity() {
        return splitQuantity;
    }

    public void setSplitQuantity(Integer splitQuantity) {
        this.splitQuantity = splitQuantity;
    }

    public Integer getSplitUnitsId() {
        return splitUnitsId;
    }

    public void setSplitUnitsId(Integer splitUnitsId) {
        this.splitUnitsId = splitUnitsId;
    }

    public BigDecimal getSplitPrice() {
        return splitPrice;
    }

    public void setSplitPrice(BigDecimal splitPrice) {
        this.splitPrice = splitPrice;
    }

    public Integer getDoseTypeId() {
        return doseTypeId;
    }

    public void setDoseTypeId(Integer doseTypeId) {
        this.doseTypeId = doseTypeId;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public Integer getDoseUnitsId() {
        return doseUnitsId;
    }

    public void setDoseUnitsId(Integer doseUnitsId) {
        this.doseUnitsId = doseUnitsId;
    }

    public Integer getSpecialDrugId() {
        return specialDrugId;
    }

    public void setSpecialDrugId(Integer specialDrugId) {
        this.specialDrugId = specialDrugId;
    }

    public Integer getDrugUsageId() {
        return drugUsageId;
    }

    public void setDrugUsageId(Integer drugUsageId) {
        this.drugUsageId = drugUsageId;
    }

    public Boolean getOdored() {
        return odored;
    }

    public void setOdored(Boolean odored) {
        this.odored = odored;
    }

    public Integer getStorageTemperatureId() {
        return storageTemperatureId;
    }

    public void setStorageTemperatureId(Integer storageTemperatureId) {
        this.storageTemperatureId = storageTemperatureId;
    }

    public Short getConservationDays() {
        return conservationDays;
    }

    public void setConservationDays(Short conservationDays) {
        this.conservationDays = conservationDays;
    }

    public Boolean getPrescription() {
        return prescription;
    }

    public void setPrescription(Boolean prescription) {
        this.prescription = prescription;
    }

    public Integer getPrescriptionTypeId() {
        return prescriptionTypeId;
    }

    public void setPrescriptionTypeId(Integer prescriptionTypeId) {
        this.prescriptionTypeId = prescriptionTypeId;
    }

    public Boolean getYbDrug() {
        return ybDrug;
    }

    public void setYbDrug(Boolean ybDrug) {
        this.ybDrug = ybDrug;
    }

    public String getYbOid() {
        return ybOid;
    }

    public void setYbOid(String ybOid) {
        this.ybOid = ybOid == null ? null : ybOid.trim();
    }

    public BigDecimal getYbPrice() {
        return ybPrice;
    }

    public void setYbPrice(BigDecimal ybPrice) {
        this.ybPrice = ybPrice;
    }

    public String getApprovalNum() {
        return approvalNum;
    }

    public void setApprovalNum(String approvalNum) {
        this.approvalNum = approvalNum == null ? null : approvalNum.trim();
    }

    public Date getApprovalNumExpiryDate() {
        return approvalNumExpiryDate;
    }

    public void setApprovalNumExpiryDate(Date approvalNumExpiryDate) {
        this.approvalNumExpiryDate = approvalNumExpiryDate;
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

    public Integer getLastUpdaterId() {
        return lastUpdaterId;
    }

    public void setLastUpdaterId(Integer lastUpdaterId) {
        this.lastUpdaterId = lastUpdaterId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
