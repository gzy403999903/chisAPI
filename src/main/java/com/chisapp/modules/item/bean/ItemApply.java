package com.chisapp.modules.item.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemApply implements Serializable {
    private Integer id; // 主键

    @Length(max = 50)
    @NotBlank
    private String name; // 项目名称

    @Length(max = 50)
    @NotBlank
    private String code; // 助记码

    private Integer cimItemTypeId; // 项目类型ID

    @NotNull
    private Integer itemUnitsId; // 项目单位ID

    private Integer befitGenderId; // 适用性别ID

    @NotNull
    private Integer billingTypeId; // 计费类型ID

    private Integer itemClassifyId; // 项目分类

    @Max(100)
    @Min(1)
    @NotNull
    private Integer quantity; // 包含次数

    @Digits(integer = 6, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal retailPrice; // 零售价格

    @Length(max = 50)
    private String explains; // 项目说明

    @NotNull
    private Boolean discountable; // 是否可打折

    @NotNull
    private Boolean returnable; // 是否可退

    @NotNull
    private Boolean ybItem; // 是否医保项目

    private Integer creatorId; // 创建人/申请人ID

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate; // 创建日期

    private Integer pricerId; // 定价人ID

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date pricingDate; // 定价日期

    private Integer approverId; // 审批人ID

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date approveDate; // 审批日期

    private Byte approveState;

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

    public Integer getCimItemTypeId() {
        return cimItemTypeId;
    }

    public void setCimItemTypeId(Integer cimItemTypeId) {
        this.cimItemTypeId = cimItemTypeId;
    }

    public Integer getItemUnitsId() {
        return itemUnitsId;
    }

    public void setItemUnitsId(Integer itemUnitsId) {
        this.itemUnitsId = itemUnitsId;
    }

    public Integer getBefitGenderId() {
        return befitGenderId;
    }

    public void setBefitGenderId(Integer befitGenderId) {
        this.befitGenderId = befitGenderId;
    }

    public Integer getBillingTypeId() {
        return billingTypeId;
    }

    public void setBillingTypeId(Integer billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public Integer getItemClassifyId() {
        return itemClassifyId;
    }

    public void setItemClassifyId(Integer itemClassifyId) {
        this.itemClassifyId = itemClassifyId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
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

    public Boolean getYbItem() {
        return ybItem;
    }

    public void setYbItem(Boolean ybItem) {
        this.ybItem = ybItem;
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

    public Integer getPricerId() {
        return pricerId;
    }

    public void setPricerId(Integer pricerId) {
        this.pricerId = pricerId;
    }

    public Date getPricingDate() {
        return pricingDate;
    }

    public void setPricingDate(Date pricingDate) {
        this.pricingDate = pricingDate;
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
