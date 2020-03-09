package com.chisapp.common.utils;

import java.math.BigDecimal;

/**
 * @Author: Tandy
 * @Date: 2019/10/18 11:25
 * @Version 1.0
 */
public class DecimalUtils {

    /**
     * 返回 四舍五入后保留 4 位小数的 Float 值
     * @param value
     * @return
     */
    public static Float roundHalfUp(Float value, Integer scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


}
