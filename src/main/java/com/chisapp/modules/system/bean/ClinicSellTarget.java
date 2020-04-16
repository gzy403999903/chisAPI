package com.chisapp.modules.system.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ClinicSellTarget implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private Integer january = 0;
    @NotNull
    private Integer february = 0;
    @NotNull
    private Integer march = 0;
    @NotNull
    private Integer april = 0;
    @NotNull
    private Integer may = 0;
    @NotNull
    private Integer june = 0;
    @NotNull
    private Integer july = 0;
    @NotNull
    private Integer august = 0;
    @NotNull
    private Integer september = 0;
    @NotNull
    private Integer october = 0;
    @NotNull
    private Integer november = 0;
    @NotNull
    private Integer december = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJanuary() {
        return january;
    }

    public void setJanuary(Integer january) {
        this.january = january;
    }

    public Integer getFebruary() {
        return february;
    }

    public void setFebruary(Integer february) {
        this.february = february;
    }

    public Integer getMarch() {
        return march;
    }

    public void setMarch(Integer march) {
        this.march = march;
    }

    public Integer getApril() {
        return april;
    }

    public void setApril(Integer april) {
        this.april = april;
    }

    public Integer getMay() {
        return may;
    }

    public void setMay(Integer may) {
        this.may = may;
    }

    public Integer getJune() {
        return june;
    }

    public void setJune(Integer june) {
        this.june = june;
    }

    public Integer getJuly() {
        return july;
    }

    public void setJuly(Integer july) {
        this.july = july;
    }

    public Integer getAugust() {
        return august;
    }

    public void setAugust(Integer august) {
        this.august = august;
    }

    public Integer getSeptember() {
        return september;
    }

    public void setSeptember(Integer september) {
        this.september = september;
    }

    public Integer getOctober() {
        return october;
    }

    public void setOctober(Integer october) {
        this.october = october;
    }

    public Integer getNovember() {
        return november;
    }

    public void setNovember(Integer november) {
        this.november = november;
    }

    public Integer getDecember() {
        return december;
    }

    public void setDecember(Integer december) {
        this.december = december;
    }
}
