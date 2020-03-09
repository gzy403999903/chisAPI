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
@RequestMapping("/westernDrugs")
@RestController
public class WesternDrugsHandler extends GoodsHandler {
    public WesternDrugsHandler () {
        GSM_GOODS_TYPE_ID = GoodsTypeEnum.WESTERN_DRUGS.getIndex();
    }
}
