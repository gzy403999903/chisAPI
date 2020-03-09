package com.chisapp.modules.goods.handler;

import com.chisapp.modules.goods.bean.GoodsType;
import com.chisapp.modules.goods.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/8/21 12:00
 * @Version 1.0
 */
@RequestMapping("/goodsType")
@RestController
public class GoodsTypeHandler {

    private GoodsTypeService goodsTypeService;
    @Autowired
    public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }

    /**
     * 获取所有对象集合
     * @return
     */
    @GetMapping("/getAll")
    public List<GoodsType> getAll () {
        return goodsTypeService.getAll();
    }
}
