package com.chisapp.modules.goods.handlerextend;

import com.chisapp.common.enums.GoodsTypeEnum;
import com.chisapp.modules.goods.handler.GoodsHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/8/19 16:52
 * @Version 1.0
 */
@RequestMapping("/hygienicMaterial")
@RestController
public class HygienicMaterialHandler extends GoodsHandler {
    public HygienicMaterialHandler() {
        GSM_GOODS_TYPE_ID = GoodsTypeEnum.HYGIENIC_MATERIAL.getIndex();
    }
}
