package com.chisapp.modules.system.dicthandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;

/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/toiletType")
@RestController
public class ToiletTypeHandler extends DictHandler {
    public ToiletTypeHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.TOILET_TYPE.getIndex();
    }
}
