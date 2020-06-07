package com.chisapp.modules.purchase.bean;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AssessCost implements Serializable {
    private Integer id;

    @NotNull
    private Integer pemSupplierId;

    @NotNull
    private Integer gsmGoodsId;

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal firstCostPrice = new BigDecimal("0");

    @Min(0)
    @Digits(integer = 8, fraction = 4)
    @NotNull
    private BigDecimal secondCostPrice = new BigDecimal("0");

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

    public Integer getPemSupplierId() {
        return pemSupplierId;
    }

    public void setPemSupplierId(Integer pemSupplierId) {
        this.pemSupplierId = pemSupplierId;
    }

    public Integer getGsmGoodsId() {
        return gsmGoodsId;
    }

    public void setGsmGoodsId(Integer gsmGoodsId) {
        this.gsmGoodsId = gsmGoodsId;
    }

    public BigDecimal getFirstCostPrice() {
        return firstCostPrice;
    }

    public void setFirstCostPrice(BigDecimal firstCostPrice) {
        this.firstCostPrice = firstCostPrice;
    }

    public BigDecimal getSecondCostPrice() {
        return secondCostPrice;
    }

    public void setSecondCostPrice(BigDecimal secondCostPrice) {
        this.secondCostPrice = secondCostPrice;
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
