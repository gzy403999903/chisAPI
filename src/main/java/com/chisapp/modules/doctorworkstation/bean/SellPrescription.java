package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SellPrescription extends SellPrescriptionAttach implements Serializable {
    private Integer id;

    @NotNull
    private Integer sysSellTypeId;

    @NotNull
    private Integer dwtClinicalHistoryId;

    @NotNull
    private Integer mrmMemberId;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @Length(max = 50)
    @NotBlank
    private String mxh;

    @NotNull
    private Integer entityTypeId;

    @NotNull
    private Integer entityId;

    @Length(max = 50)
    @NotBlank
    private String name;

    @Length(max = 20)
    private String specs;

    @Max(30000)
    @Min(1)
    @NotNull
    private Integer quantity;

    @Length(max = 20)
    @NotBlank
    private String unitsName;

    @Min(1)
    private Integer foldQuantity = 1;

    @Min(1)
    private Integer splitQuantity = 1;

    @Length(max = 300)
    private String sigJson;

    @NotNull
    private Integer sysClinicId;

    @NotNull
    private Integer sysDoctorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysSellTypeId() {
        return sysSellTypeId;
    }

    public void setSysSellTypeId(Integer sysSellTypeId) {
        this.sysSellTypeId = sysSellTypeId;
    }

    public Integer getDwtClinicalHistoryId() {
        return dwtClinicalHistoryId;
    }

    public void setDwtClinicalHistoryId(Integer dwtClinicalHistoryId) {
        this.dwtClinicalHistoryId = dwtClinicalHistoryId;
    }

    public Integer getMrmMemberId() {
        return mrmMemberId;
    }

    public void setMrmMemberId(Integer mrmMemberId) {
        this.mrmMemberId = mrmMemberId;
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

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs == null ? null : specs.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName == null ? null : unitsName.trim();
    }

    public Integer getFoldQuantity() {
        return foldQuantity;
    }

    public void setFoldQuantity(Integer foldQuantity) {
        this.foldQuantity = foldQuantity;
    }

    public Integer getSplitQuantity() {
        return splitQuantity;
    }

    public void setSplitQuantity(Integer splitQuantity) {
        this.splitQuantity = splitQuantity;
    }

    public String getSigJson() {
        return sigJson;
    }

    public void setSigJson(String sigJson) {
        this.sigJson = sigJson == null ? null : sigJson.trim();
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getSysDoctorId() {
        return sysDoctorId;
    }

    public void setSysDoctorId(Integer sysDoctorId) {
        this.sysDoctorId = sysDoctorId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
