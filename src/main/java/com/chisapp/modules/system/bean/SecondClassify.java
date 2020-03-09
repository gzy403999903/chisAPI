package com.chisapp.modules.system.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SecondClassify implements Serializable {
    private Integer id;

    @NotNull
    private Integer goodsClassifyId;

    @Length(max = 30)
    @NotNull
    private String name;

    @Length(max = 30)
    @NotNull
    private String code;

    @NotNull
    private Boolean state;

    private Boolean required = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(Integer goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
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
