package com.chisapp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date getPastDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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

    /**
     * 将日期字符串 或 日期 格式化为 yyyy-MM-dd 格式
     * @param date
     * @return
     */
    public static Date parseToShort(Object date) {
        Date parseDate;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (date.getClass().getName().equals("java.lang.String")) {
                date = df.parse(date.toString());
            }
            parseDate = df.parse(df.format(date));
        } catch (ParseException e) {
            throw new RuntimeException("日期格式不正确");
        }

        return parseDate;
    }


}
