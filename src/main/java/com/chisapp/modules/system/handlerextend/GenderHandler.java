package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/7/28 20:57
 * @Version 1.0
 */
@RequestMapping("/gender")
@RestController
public class GenderHandler extends DictHandler{
    public GenderHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.GENDER.getIndex();
    }
}
