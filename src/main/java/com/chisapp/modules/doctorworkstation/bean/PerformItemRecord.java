package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PerformItemRecord implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @NotNull
    private Integer dwtPerformItemId;

    @NotNull
    private Integer performQuantity;

    @NotNull
    private Integer residueQuantity;

    @NotNull
    private Integer sysClinicId;

    @NotNull
    private Integer operatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

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

    public Integer getDwtPerformItemId() {
        return dwtPerformItemId;
    }

    public void setDwtPerformItemId(Integer dwtPerformItemId) {
        this.dwtPerformItemId = dwtPerformItemId;
    }

    public Integer getPerformQuantity() {
        return performQuantity;
    }

    public void setPerformQuantity(Integer performQuantity) {
        this.performQuantity = performQuantity;
    }

    public Integer getResidueQuantity() {
        return residueQuantity;
    }

    public void setResidueQuantity(Integer residueQuantity) {
        this.residueQuantity = residueQuantity;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
