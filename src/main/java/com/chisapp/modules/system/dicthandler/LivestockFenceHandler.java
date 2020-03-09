package com.chisapp.modules.system.dicthandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/livestockFence")
@RestController
public class LivestockFenceHandler extends DictHandler {
    public LivestockFenceHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.LIVESTOCK_FENCE.getIndex();
    }
}
