package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class CommonDiagnose implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotNull
    private String name;

    @Length(max = 50)
    @NotNull
    private String code;

    private Integer dwtDiagnoseTypeId;

    private Boolean shareState = false;

    private Boolean normative = false;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationDate;

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

    public Integer getDwtDiagnoseTypeId() {
        return dwtDiagnoseTypeId;
    }

    public void setDwtDiagnoseTypeId(Integer dwtDiagnoseTypeId) {
        this.dwtDiagnoseTypeId = dwtDiagnoseTypeId;
    }

    public Boolean getShareState() {
        return shareState;
    }

    public void setShareState(Boolean shareState) {
        this.shareState = shareState;
    }

    public Boolean getNormative() {
        return normative;
    }

    public void setNormative(Boolean normative) {
        this.normative = normative;
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
}
