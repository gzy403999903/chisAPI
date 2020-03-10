package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DiagnoseType implements Serializable {
    private Integer id;

    @Length(max = 20)
    @NotNull
    private String name;

    @Length(max = 20)
    @NotNull
    private String code;

    @NotNull
    private Boolean state = true;

    @NotNull
    private Boolean required = true;

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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
