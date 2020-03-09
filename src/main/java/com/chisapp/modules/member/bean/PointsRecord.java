package com.chisapp.modules.member.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PointsRecord implements Serializable {
    private Integer id;

    @Length(max = 50)
    private String lsh;

    @NotNull
    private Integer mrmMemberId;

    @NotNull
    private Integer exchangePoints;

    @NotNull
    private Integer givenPoints;

    @Min(0)
    @NotNull
    private Integer points;

    private Integer sysClinicId;

    private Integer creatorId;

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

    public Integer getMrmMemberId() {
        return mrmMemberId;
    }

    public void setMrmMemberId(Integer mrmMemberId) {
        this.mrmMemberId = mrmMemberId;
    }

    public Integer getExchangePoints() {
        return exchangePoints;
    }

    public void setExchangePoints(Integer exchangePoints) {
        this.exchangePoints = exchangePoints;
    }

    public Integer getGivenPoints() {
        return givenPoints;
    }

    public void setGivenPoints(Integer givenPoints) {
        this.givenPoints = givenPoints;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
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
