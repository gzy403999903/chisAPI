package com.chisapp.modules.member.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Member implements Serializable {
    private Integer id;

    @Length(max = 30)
    @NotBlank
    private String oid;

    @Length(max = 10)
    @NotBlank
    private String name;

    @Length(max = 10)
    @NotBlank
    private String code;

    @NotNull
    private Integer genderId;

    @Length(max = 30)
    private String idCardNo;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    @Past
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birth;

    private Integer nationalityId;

    @Length(max = 15)
    private String phone;

    private Integer maritalId;

    private Integer educationId;

    private Boolean domicilePlace;

    private Integer bloodTypeId;

    private Integer rhId;

    @Length(max = 50)
    private String company;

    private Integer professionId;

    private Integer mrmTownshipId;

    private Integer mrmCommitteeId;

    @Length(max = 50)
    private String address;

    @Length(max = 50)
    private String dpAddress;

    @Length(max = 10)
    private String contactName;

    @Length(max = 15)
    private String contactPhone;

    private Integer doctorId;

    @Length(max = 15)
    private String doctorPhone;

    private Integer mrmMemberTypeId;

    @Email
    private String eMail;

    @Length(max = 50)
    private String notes;

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal balance = new BigDecimal("0");

    @Digits(integer = 8, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal givenBalance = new BigDecimal("0");

    @Min(0)
    @NotNull
    private Integer points = 0;

    @Length(max = 255)
    private String imageUrl;

    private Integer committeeArchiveIndex;

    @Length(max = 17)
    private String archiveNo;

    @Length(max = 50)
    private String geneticIllness;

    private Integer kitchenExhaustId;

    private Integer fuelTypeId;

    private Integer waterSourceId;

    private Integer toiletTypeId;

    private Integer livestockFenceId;

    private Boolean archiveState = false;

    private Boolean state = true;

    private Integer sysClinicId;

    private Integer creatorId;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

    private Integer lastUpdaterId;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
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

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getMaritalId() {
        return maritalId;
    }

    public void setMaritalId(Integer maritalId) {
        this.maritalId = maritalId;
    }

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public Boolean getDomicilePlace() {
        return domicilePlace;
    }

    public void setDomicilePlace(Boolean domicilePlace) {
        this.domicilePlace = domicilePlace;
    }

    public Integer getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(Integer bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public Integer getRhId() {
        return rhId;
    }

    public void setRhId(Integer rhId) {
        this.rhId = rhId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public Integer getMrmTownshipId() {
        return mrmTownshipId;
    }

    public void setMrmTownshipId(Integer mrmTownshipId) {
        this.mrmTownshipId = mrmTownshipId;
    }

    public Integer getMrmCommitteeId() {
        return mrmCommitteeId;
    }

    public void setMrmCommitteeId(Integer mrmCommitteeId) {
        this.mrmCommitteeId = mrmCommitteeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDpAddress() {
        return dpAddress;
    }

    public void setDpAddress(String dpAddress) {
        this.dpAddress = dpAddress == null ? null : dpAddress.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone == null ? null : doctorPhone.trim();
    }

    public Integer getMrmMemberTypeId() {
        return mrmMemberTypeId;
    }

    public void setMrmMemberTypeId(Integer mrmMemberTypeId) {
        this.mrmMemberTypeId = mrmMemberTypeId;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getGivenBalance() {
        return givenBalance;
    }

    public void setGivenBalance(BigDecimal givenBalance) {
        this.givenBalance = givenBalance;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getCommitteeArchiveIndex() {
        return committeeArchiveIndex;
    }

    public void setCommitteeArchiveIndex(Integer committeeArchiveIndex) {
        this.committeeArchiveIndex = committeeArchiveIndex;
    }

    public String getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo == null ? null : archiveNo.trim();
    }

    public String getGeneticIllness() {
        return geneticIllness;
    }

    public void setGeneticIllness(String geneticIllness) {
        this.geneticIllness = geneticIllness == null ? null : geneticIllness.trim();
    }

    public Integer getKitchenExhaustId() {
        return kitchenExhaustId;
    }

    public void setKitchenExhaustId(Integer kitchenExhaustId) {
        this.kitchenExhaustId = kitchenExhaustId;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Integer getWaterSourceId() {
        return waterSourceId;
    }

    public void setWaterSourceId(Integer waterSourceId) {
        this.waterSourceId = waterSourceId;
    }

    public Integer getToiletTypeId() {
        return toiletTypeId;
    }

    public void setToiletTypeId(Integer toiletTypeId) {
        this.toiletTypeId = toiletTypeId;
    }

    public Integer getLivestockFenceId() {
        return livestockFenceId;
    }

    public void setLivestockFenceId(Integer livestockFenceId) {
        this.livestockFenceId = livestockFenceId;
    }

    public Boolean getArchiveState() {
        return archiveState;
    }

    public void setArchiveState(Boolean archiveState) {
        this.archiveState = archiveState;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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
