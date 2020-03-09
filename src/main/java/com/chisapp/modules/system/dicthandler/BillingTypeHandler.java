package com.chisapp.modules.system.dicthandler;

import com.chisapp.common.enums.DictTypeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/7/28 20:57
 * @Version 1.0
 */
@RequestMapping("/billingType")
@RestController
public class BillingTypeHandler extends DictHandler{
    public BillingTypeHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.BILLING_TYPE.getIndex();
    }
}
