package com.chisapp.modules.system.dicthandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chisapp.common.enums.DictTypeEnum;
/**
 * @Author: Tandy
 * @Date: 2019/8/13 16:29
 * @Version 1.0
 */
@RequestMapping("/goodsClassify")
@RestController
public class GoodsClassifyHandler extends DictHandler {
    public GoodsClassifyHandler() {
        super.SYS_DICT_TYPE_ID = DictTypeEnum.GOODS_CLASSIFY.getIndex();
    }
}
