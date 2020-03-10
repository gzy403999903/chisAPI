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
@RequestMapping("/chineseCommonDiagnose")
@RestController
public class ChineseCommonDiagnoseHandler extends CommonDiagnoseHandler {
    public ChineseCommonDiagnoseHandler() {
        super.DWT_DIAGNOSE_TYPE_ID = DiagnoseTypeEnum.CHINESE_DIAGNOSE.getIndex();
    }
}
