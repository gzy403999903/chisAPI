package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/goodsUnits")
@RestController
public class GoodsUnitsHandler extends DictHandler {
    public GoodsUnitsHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.GOODS_UNITS.getIndex();
    }
}
