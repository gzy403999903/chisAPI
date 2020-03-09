package com.chisapp.modules.system.dicthandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/doseType")
@RestController
public class DoseTypeHandler extends DictHandler {
    public DoseTypeHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.DOSE_TYPE.getIndex();
    }
}
