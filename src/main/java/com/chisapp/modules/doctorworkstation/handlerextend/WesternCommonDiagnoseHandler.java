package com.chisapp.modules.doctorworkstation.handlerextend;

import com.chisapp.common.enums.DiagnoseTypeEnum;
import com.chisapp.modules.doctorworkstation.handler.CommonDiagnoseHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/11/6 16:20
 * @Version 1.0
 */
@RequestMapping("/westernCommonDiagnose")
@RestController
public class WesternCommonDiagnoseHandler extends CommonDiagnoseHandler {
    public WesternCommonDiagnoseHandler() {
        super.DWT_DIAGNOSE_TYPE_ID = DiagnoseTypeEnum.WESTERN_DIAGNOSE.getIndex();
    }
}
