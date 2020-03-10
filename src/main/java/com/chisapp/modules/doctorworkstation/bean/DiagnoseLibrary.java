package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DiagnoseLibrary implements Serializable {
    private String oid;

    @Length(max = 50)
    @NotNull
    private String name;

    @Length(max = 50)
    @NotNull
    private String code;

    @NotNull
    private Integer dwtDiagnoseClassifyId;

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

    public Integer getDwtDiagnoseClassifyId() {
        return dwtDiagnoseClassifyId;
    }

    public void setDwtDiagnoseClassifyId(Integer dwtDiagnoseClassifyId) {
        this.dwtDiagnoseClassifyId = dwtDiagnoseClassifyId;
    }
}
