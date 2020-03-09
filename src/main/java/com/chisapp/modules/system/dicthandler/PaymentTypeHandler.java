package com.chisapp.modules.system.dicthandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;

/**
 * @Author: Tandy
 * @Date: 2019/7/28 20:57
 * @Version 1.0
 */
@RequestMapping("/paymentType")
@RestController
public class PaymentTypeHandler extends DictHandler{
    public PaymentTypeHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.PAYMENT_TYPE.getIndex();
    }
}
