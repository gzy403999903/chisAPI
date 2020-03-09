package com.chisapp.modules.system.handlerextend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/drugsPrepareMethod")
@RestController
public class DrugsPrepareMethodHandler extends DictHandler {
    public DrugsPrepareMethodHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.DRUGS_PREPARE_METHOD.getIndex();
    }
}
