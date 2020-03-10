package com.chisapp.modules.doctorworkstation.bean;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PerformItem implements Serializable {
    private Integer id;

    @Length(max = 50)
    @NotBlank
    private String lsh;

    @NotNull
    private Integer mrmMemberId;

    @NotNull
    private Integer cimItemId;

    @NotNull
    @Min(1)
    private Integer quantity = 1;

    @NotNull
    @Min(1)
    private Integer onceContainQuantity = 1;

    @NotNull
    @Min(0)
    private Integer residueQuantity;

    @NotNull
    private Integer sellerId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
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

    public Integer getCimItemId() {
        return cimItemId;
    }

    public void setCimItemId(Integer cimItemId) {
        this.cimItemId = cimItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOnceContainQuantity() {
        return onceContainQuantity;
    }

    public void setOnceContainQuantity(Integer onceContainQuantity) {
        this.onceContainQuantity = onceContainQuantity;
    }

    public Integer getResidueQuantity() {
        return residueQuantity;
    }

    public void setResidueQuantity(Integer residueQuantity) {
        this.residueQuantity = residueQuantity;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
