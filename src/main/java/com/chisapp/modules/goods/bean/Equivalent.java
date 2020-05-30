package com.chisapp.modules.goods.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Equivalent implements Serializable {
    private Integer id;

    @NotNull
    private Integer useGsmGoodsId;

    @NotNull
    private Integer useGoodsUnitsId;

    @NotNull
    private Integer referGsmGoodsId;

    @NotNull
    private Integer referGoodsUnitsId;

    @NotNull
    private Integer referQuantity;

    private Integer creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    private Integer lastUpdaterId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUseGsmGoodsId() {
        return useGsmGoodsId;
    }

    public void setUseGsmGoodsId(Integer useGsmGoodsId) {
        this.useGsmGoodsId = useGsmGoodsId;
    }

    public Integer getUseGoodsUnitsId() {
        return useGoodsUnitsId;
    }

    public void setUseGoodsUnitsId(Integer useGoodsUnitsId) {
        this.useGoodsUnitsId = useGoodsUnitsId;
    }

    public Integer getReferGsmGoodsId() {
        return referGsmGoodsId;
    }

    public void setReferGsmGoodsId(Integer referGsmGoodsId) {
        this.referGsmGoodsId = referGsmGoodsId;
    }

    public Integer getReferGoodsUnitsId() {
        return referGoodsUnitsId;
    }

    public void setReferGoodsUnitsId(Integer referGoodsUnitsId) {
        this.referGoodsUnitsId = referGoodsUnitsId;
    }

    public Integer getReferQuantity() {
        return referQuantity;
    }

    public void setReferQuantity(Integer referQuantity) {
        this.referQuantity = referQuantity;
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

    public Integer getLastUpdaterId() {
        return lastUpdaterId;
    }

    public void setLastUpdaterId(Integer lastUpdaterId) {
        this.lastUpdaterId = lastUpdaterId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
