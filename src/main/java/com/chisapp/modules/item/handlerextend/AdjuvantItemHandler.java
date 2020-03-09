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
@RequestMapping("/adjuvantItem")
@RestController
public class AdjuvantItemHandler extends ItemHandler {

    public AdjuvantItemHandler() {
        super.CIM_ITEM_TYPE_ID = ItemTypeEnum.ADJUVANT_ITEM.getIndex();
    }
}
