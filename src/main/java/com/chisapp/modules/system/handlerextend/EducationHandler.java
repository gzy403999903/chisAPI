package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/10/20 12:47
 * @Version 1.0
 */
@RequestMapping("/education")
@RestController
public class EducationHandler extends DictHandler {
    public EducationHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.EDUCATION.getIndex();
    }
}
