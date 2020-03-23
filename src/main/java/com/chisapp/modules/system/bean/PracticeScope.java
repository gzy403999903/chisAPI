package com.chisapp.modules.system.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PracticeScope implements Serializable {
    private Integer id;

    @NotNull
    private Integer practiceTypeId;

    @Length(max = 30)
    @NotBlank
    private String name;

    @Length(max = 30)
    @NotBlank
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPracticeTypeId() {
        return practiceTypeId;
    }

    public void setPracticeTypeId(Integer practiceTypeId) {
        this.practiceTypeId = practiceTypeId;
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
}
