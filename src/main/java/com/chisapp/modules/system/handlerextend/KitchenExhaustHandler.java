package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/kitchenExhaust")
@RestController
public class KitchenExhaustHandler extends DictHandler {
    public KitchenExhaustHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.KITCHEN_EXHAUST.getIndex();
    }
}
