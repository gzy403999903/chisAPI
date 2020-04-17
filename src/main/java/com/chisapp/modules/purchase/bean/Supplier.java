package com.chisapp.modules.purchase.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Supplier implements Serializable {
    private Integer id;

    @Length(max = 30)
    @NotBlank
    private String oid;

    @Length(max = 30)
    @NotBlank
    private String name;

    @Length(max = 30)
    @NotBlank
    private String code;

    @Length(max = 30)
    // @NotBlank
    private String accountLicence;

    @Length(max = 30)
    @NotBlank
    private String bankName;

    @Length(max = 30)
    @NotBlank
    private String cardNo;

    @NotNull
    private Integer invoiceTypeId;

    @NotNull
    private Integer paymentTypeId;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal arrearagesAmount = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal arrearagesLimit = new BigDecimal("0");

    @Min(0)
    @NotNull
    private Byte arrearagesDays;

    @Length(max = 10)
    @NotBlank
    private String legalPerson;

    @Length(max = 30)
    @NotBlank
    private String tel;

    @Length(max = 30)
    private String fax;

    @NotNull
    private Integer taxpayerTypeId;

    @Length(max = 50)
    @NotBlank
    private String address;

    @Length(max = 10)
    @NotBlank
    private String contacter;

    @Length(max = 30)
    @NotBlank
    private String contacterPhone;

    @Length(max = 300)
    @NotBlank
    private String businessScope;

    @Length(max = 100)
    private String remarks;

    @Length(max = 30)
    private String businessLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date blExpiryDate;

    @Length(max = 30)
    private String hygienicLicense;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date hlExpiryDate;

    @Length(max = 30)
    private String productionLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date plExpiryDate;

    @Length(max = 30)
    private String gspLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date gspExpiryDate;

    @Length(max = 30)
    private String gmpLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date gmpExpiryDate;

    @Length(max = 30)
    private String instrumentLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date ilExpiryDate;

    @Length(max = 30)
    private String foodLicense;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date flExpiryDate;

    @Length(max = 30)
    private String foodProductionLicence;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fplExpiryDate;

    private Boolean state;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    private Integer lastUpdaterId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAccountLicence() {
        return accountLicence;
    }

    public void setAccountLicence(String accountLicence) {
        this.accountLicence = accountLicence == null ? null : accountLicence.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Integer getInvoiceTypeId() {
        return invoiceTypeId;
    }

    public void setInvoiceTypeId(Integer invoiceTypeId) {
        this.invoiceTypeId = invoiceTypeId;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public BigDecimal getArrearagesAmount() {
        return arrearagesAmount;
    }

    public void setArrearagesAmount(BigDecimal arrearagesAmount) {
        this.arrearagesAmount = arrearagesAmount;
    }

    public BigDecimal getArrearagesLimit() {
        return arrearagesLimit;
    }

    public void setArrearagesLimit(BigDecimal arrearagesLimit) {
        this.arrearagesLimit = arrearagesLimit;
    }

    public Byte getArrearagesDays() {
        return arrearagesDays;
    }

    public void setArrearagesDays(Byte arrearagesDays) {
        this.arrearagesDays = arrearagesDays;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Integer getTaxpayerTypeId() {
        return taxpayerTypeId;
    }

    public void setTaxpayerTypeId(Integer taxpayerTypeId) {
        this.taxpayerTypeId = taxpayerTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter == null ? null : contacter.trim();
    }

    public String getContacterPhone() {
        return contacterPhone;
    }

    public void setContacterPhone(String contacterPhone) {
        this.contacterPhone = contacterPhone == null ? null : contacterPhone.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence == null ? null : businessLicence.trim();
    }

    public Date getBlExpiryDate() {
        return blExpiryDate;
    }

    public void setBlExpiryDate(Date blExpiryDate) {
        this.blExpiryDate = blExpiryDate;
    }

    public String getHygienicLicense() {
        return hygienicLicense;
    }

    public void setHygienicLicense(String hygienicLicense) {
        this.hygienicLicense = hygienicLicense == null ? null : hygienicLicense.trim();
    }

    public Date getHlExpiryDate() {
        return hlExpiryDate;
    }

    public void setHlExpiryDate(Date hlExpiryDate) {
        this.hlExpiryDate = hlExpiryDate;
    }

    public String getProductionLicence() {
        return productionLicence;
    }

    public void setProductionLicence(String productionLicence) {
        this.productionLicence = productionLicence == null ? null : productionLicence.trim();
    }

    public Date getPlExpiryDate() {
        return plExpiryDate;
    }

    public void setPlExpiryDate(Date plExpiryDate) {
        this.plExpiryDate = plExpiryDate;
    }

    public String getGspLicence() {
        return gspLicence;
    }

    public void setGspLicence(String gspLicence) {
        this.gspLicence = gspLicence == null ? null : gspLicence.trim();
    }

    public Date getGspExpiryDate() {
        return gspExpiryDate;
    }

    public void setGspExpiryDate(Date gspExpiryDate) {
        this.gspExpiryDate = gspExpiryDate;
    }

    public String getGmpLicence() {
        return gmpLicence;
    }

    public void setGmpLicence(String gmpLicence) {
        this.gmpLicence = gmpLicence == null ? null : gmpLicence.trim();
    }

    public Date getGmpExpiryDate() {
        return gmpExpiryDate;
    }

    public void setGmpExpiryDate(Date gmpExpiryDate) {
        this.gmpExpiryDate = gmpExpiryDate;
    }

    public String getInstrumentLicence() {
        return instrumentLicence;
    }

    public void setInstrumentLicence(String instrumentLicence) {
        this.instrumentLicence = instrumentLicence == null ? null : instrumentLicence.trim();
    }

    public Date getIlExpiryDate() {
        return ilExpiryDate;
    }

    public void setIlExpiryDate(Date ilExpiryDate) {
        this.ilExpiryDate = ilExpiryDate;
    }

    public String getFoodLicense() {
        return foodLicense;
    }

    public void setFoodLicense(String foodLicense) {
        this.foodLicense = foodLicense == null ? null : foodLicense.trim();
    }

    public Date getFlExpiryDate() {
        return flExpiryDate;
    }

    public void setFlExpiryDate(Date flExpiryDate) {
        this.flExpiryDate = flExpiryDate;
    }

    public String getFoodProductionLicence() {
        return foodProductionLicence;
    }

    public void setFoodProductionLicence(String foodProductionLicence) {
        this.foodProductionLicence = foodProductionLicence == null ? null : foodProductionLicence.trim();
    }

    public Date getFplExpiryDate() {
        return fplExpiryDate;
    }

    public void setFplExpiryDate(Date fplExpiryDate) {
        this.fplExpiryDate = fplExpiryDate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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
