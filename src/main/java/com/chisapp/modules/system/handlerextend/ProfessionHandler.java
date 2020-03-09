package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;

/**
 * @Author: Tandy
 * @Date: 2019/7/28 20:57
 * @Version 1.0
 */
@RequestMapping("/profession")
@RestController
public class ProfessionHandler extends DictHandler{
    public ProfessionHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.PROFESSION.getIndex();
    }
}
