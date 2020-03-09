package com.chisapp.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Tandy
 * @Date: 2019/8/12 14:50
 * @Version 1.0
 */
public class DateUtils {

    /**
     * 获取距离当前指定天数的过去日期
     * @param days
     * @return
     */
    public static Date getPastDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days * -1);

        return calendar.getTime();
    }



    /**
     * 获取距离当前指定天数的未来日期
     * @param days
     * @return
     */
    public static Date getFutureDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }
}
