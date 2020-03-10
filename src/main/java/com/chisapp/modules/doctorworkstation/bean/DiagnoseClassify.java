package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DiagnoseClassify implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotNull
    private String name;

    @Length(max = 50)
    @NotNull
    private String code;

    @NotNull
    private Integer dwtDiagnoseTypeId;

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
}
