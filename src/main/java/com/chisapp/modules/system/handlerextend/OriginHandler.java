package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;

/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/origin")
@RestController
public class OriginHandler extends DictHandler {
    public OriginHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.ORIGIN.getIndex();
    }
}
