package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ClinicalHistoryTemplate implements Serializable {
    private Integer id;

    @NotBlank
    @Length(max = 20)
    private String name;

    @NotBlank
    @Length(max = 20)
    private String code;

    @Length(max = 100)
    private String chiefComplaint;

    @Length(max = 300)
    private String presentIllness;

    @Length(max = 300)
    private String physicalExamination;

    @Length(max = 300)
    private String pastHistory;

    @Length(max = 300)
    private String allergenHistory;

    @Length(max = 300)
    private String auxiliaryExamination;

    @Length(max = 300)
    private String dialectical;

    @Length(max = 300)
    private String diagnoseJson;

    @NotNull
    private Integer dwtDiagnoseTypeId;

    private Integer sysDoctorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    private Integer sysClinicId;

    @Length(max = 100)
    private String notes;

    private Boolean shareState;

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

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint == null ? null : chiefComplaint.trim();
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness == null ? null : presentIllness.trim();
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination == null ? null : physicalExamination.trim();
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory == null ? null : pastHistory.trim();
    }

    public String getAllergenHistory() {
        return allergenHistory;
    }

    public void setAllergenHistory(String allergenHistory) {
        this.allergenHistory = allergenHistory == null ? null : allergenHistory.trim();
    }

    public String getAuxiliaryExamination() {
        return auxiliaryExamination;
    }

    public void setAuxiliaryExamination(String auxiliaryExamination) {
        this.auxiliaryExamination = auxiliaryExamination == null ? null : auxiliaryExamination.trim();
    }

    public String getDialectical() {
        return dialectical;
    }

    public void setDialectical(String dialectical) {
        this.dialectical = dialectical == null ? null : dialectical.trim();
    }

    public String getDiagnoseJson() {
        return diagnoseJson;
    }

    public void setDiagnoseJson(String diagnoseJson) {
        this.diagnoseJson = diagnoseJson == null ? null : diagnoseJson.trim();
    }

    public Integer getDwtDiagnoseTypeId() {
        return dwtDiagnoseTypeId;
    }

    public void setDwtDiagnoseTypeId(Integer dwtDiagnoseTypeId) {
        this.dwtDiagnoseTypeId = dwtDiagnoseTypeId;
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

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Boolean getShareState() {
        return shareState;
    }

    public void setShareState(Boolean shareState) {
        this.shareState = shareState;
    }
}
