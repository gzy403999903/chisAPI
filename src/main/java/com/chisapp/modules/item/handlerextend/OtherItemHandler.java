package com.chisapp.modules.item.handlerextend;

import com.chisapp.common.enums.ItemTypeEnum;
import com.chisapp.modules.item.handler.ItemHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/8/7 10:10
 * @Version 1.0
 */
@RequestMapping("/otherItem")
@RestController
public class OtherItemHandler extends ItemHandler {

    public OtherItemHandler() {
        super.CIM_ITEM_TYPE_ID = ItemTypeEnum.OTHER_ITEM.getIndex();
    }
}
