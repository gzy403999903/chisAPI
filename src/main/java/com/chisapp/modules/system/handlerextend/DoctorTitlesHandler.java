package com.chisapp.modules.system.handlerextend;

import com.chisapp.common.enums.DictTypeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tandy
 * @Date: 2020-03-22 18:56
 * @Version 1.0
 */
@RequestMapping("/doctorTitles")
@RestController
public class DoctorTitlesHandler extends DictHandler {
    public DoctorTitlesHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.DOCTOR_TITLES.getIndex();
    }
}
