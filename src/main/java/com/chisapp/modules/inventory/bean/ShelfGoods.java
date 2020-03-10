package com.chisapp.modules.inventory.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ShelfGoods implements Serializable {
    private Integer id;

    @NotNull
    private Integer iymShelfPositionId;

    @NotNull
    private Integer gsmGoodsId;

    @Min(0)
    @Max(30000)
    @NotNull
    private Integer maxQuantity = 0;

    @Min(0)
    @Max(30000)
    @NotNull
    private Integer minQuantity = 0;

    private Integer sysClinicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIymShelfPositionId() {
        return iymShelfPositionId;
    }

    public void setIymShelfPositionId(Integer iymShelfPositionId) {
        this.iymShelfPositionId = iymShelfPositionId;
    }

    public Integer getGsmGoodsId() {
        return gsmGoodsId;
    }

    public void setGsmGoodsId(Integer gsmGoodsId) {
        this.gsmGoodsId = gsmGoodsId;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getSysClinicId() {
        return sysClinicId;
    }

    public void setSysClinicId(Integer sysClinicId) {
        this.sysClinicId = sysClinicId;
    }
}
