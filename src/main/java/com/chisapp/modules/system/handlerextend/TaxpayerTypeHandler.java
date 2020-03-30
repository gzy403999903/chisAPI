package com.chisapp.modules.system.handlerextend;

import com.chisapp.common.enums.DictTypeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/taxpayerType")
@RestController
public class TaxpayerTypeHandler extends DictHandler {
    public TaxpayerTypeHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.TAXPAYER_TYPE.getIndex();
    }
}
