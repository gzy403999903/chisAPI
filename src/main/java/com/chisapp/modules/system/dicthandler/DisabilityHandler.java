package com.chisapp.modules.system.dicthandler;

import com.chisapp.common.enums.DictTypeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/disability")
@RestController
public class DisabilityHandler extends DictHandler {
    public DisabilityHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.DISABILITY.getIndex();
    }
}
