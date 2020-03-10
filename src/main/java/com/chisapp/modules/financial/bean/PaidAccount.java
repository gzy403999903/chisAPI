package com.chisapp.modules.financial.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaidAccount implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotBlank
    private String flmPayableAccountLsh;

    @NotNull
    private Integer gsmGoodsId;

    @Length(max = 50)
    @NotBlank
    private String ph;

    @Length(max = 50)
    @NotBlank
    private String pch;

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal costPrice;

    @Max(30000)
    @NotNull
    private Integer quantity;

    @NotNull
    private Integer pemSupplierId;

    @NotNull
    private Integer iymInventoryAddId;

    private Integer sysClinicId;

    @Length(max = 50)
    private String invoiceNo;

    @NotNull
    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date creationDate;

    @Length(max = 50)
    private String paymentNo;

    private Integer payerId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date payDate;

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

    public String getFlmPayableAccountLsh() {
        return flmPayableAccountLsh;
    }

    public void setFlmPayableAccountLsh(String flmPayableAccountLsh) {
        this.flmPayableAccountLsh = flmPayableAccountLsh == null ? null : flmPayableAccountLsh.trim();
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
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

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo == null ? null : paymentNo.trim();
    }

    public Integer getPayerId() {
        return payerId;
    }

    public void setPayerId(Integer payerId) {
        this.payerId = payerId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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
