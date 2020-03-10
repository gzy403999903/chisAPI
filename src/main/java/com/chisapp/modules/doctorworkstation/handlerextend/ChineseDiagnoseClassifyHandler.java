package com.chisapp.modules.doctorworkstation.handlerextend;

import com.chisapp.common.enums.DiagnoseTypeEnum;
import com.chisapp.modules.doctorworkstation.handler.DiagnoseClassifyHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 12:08
 * @Version 1.0
 */
@RequestMapping("/chineseDiagnoseClassify")
@RestController
public class ChineseDiagnoseClassifyHandler extends DiagnoseClassifyHandler {

    public ChineseDiagnoseClassifyHandler() {
        super.DWT_DIAGNOSE_TYPE_ID = DiagnoseTypeEnum.CHINESE_DIAGNOSE.getIndex();
    }
}
