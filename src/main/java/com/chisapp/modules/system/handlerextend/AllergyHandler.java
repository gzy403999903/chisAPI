package com.chisapp.modules.system.handlerextend;

import com.chisapp.common.enums.DictTypeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/7/28 20:57
 * @Version 1.0
 */
@RequestMapping("/allergy")
@RestController
public class AllergyHandler extends DictHandler{
    public AllergyHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.ALLERGY.getIndex();
    }
}
