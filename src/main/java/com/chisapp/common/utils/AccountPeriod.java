package com.chisapp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 会计期间 (账期)
 *
 * 单例模式
 * @Author: Tandy
 * @Date: 2020-05-21 11:23
 * @Version 1.0
 */
public class AccountPeriod {
    private AccountPeriod() {}

    private static volatile AccountPeriod instance;

    public static AccountPeriod getInstance() {
        if(instance == null) {
            synchronized (AccountPeriod.class) {
                if(instance == null) {
                    instance = new AccountPeriod();
                }
            }
        }
        return instance;
    }

    /* ------------------------------------------------------------------------------------------------------------- */
    private LocalDate localDate = LocalDate.now();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将 Date 转为 LocalDate
     * @param date
     * @return
     */
    private LocalDate parseToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 获取年份
     * @return
     */
    public int getYear() {
        return this.localDate.getYear();
    }

    public int getYear(Date date) {
        return this.parseToLocalDate(date).getYear();
    }

    public int getPrevYear(Date date) {
        int month = this.getMonth(date) - 1;
        int year = this.getYear(date);
        return month < 1 ? (year -1) : year;
    }

    /**
     * 获取月份
     * @return
     */
    public int getMonth() {
        int currentMonth = this.localDate.getMonthValue();
        int day = this.localDate.getDayOfMonth();
        int month = day <= 25 ? currentMonth : currentMonth + 1;
        return month > 12 ? 12 : month;
    }

    public int getMonth(Date date) {
        LocalDate localDate = this.parseToLocalDate(date);
        int currentMonth = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        int month = day <= 25 ? currentMonth : currentMonth + 1;
        return month > 12 ? 12 : month;
    }

    public int getPrevMonth(Date date) {
        int month = this.getMonth(date) - 1;
        return month < 1 ? 12 : month;
    }


    /**
     * 获取起始日期
     * @return
     */
    public Date getBeginDate() {
        int year = this.getYear();
        int month = this.getMonth() == 1 ? 1 : this.getMonth() - 1;
        int day = month == 1 ? 1 : 26;

        Date beginDate;
        try {
            beginDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            throw new RuntimeException("不正确的格式日期");
        }
        return beginDate;
    }

    public Date getBeginDate(Date date) {
        int year = this.getYear(date);
        int month = this.getMonth(date) == 1 ? 1 : this.getMonth(date) - 1;
        int day = month == 1 ? 1 : 26;

        Date beginDate;
        try {
            beginDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            throw new RuntimeException("不正确的格式日期");
        }
        return beginDate;
    }

    /**
     * 获取结束日期
     * @return
     */
    public Date getEndDate() {
        int year = this.getYear();
        int month = this.getMonth();
        int day = month == 12 ? 31 : 25;

        Date endDate;
        try {
            endDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            throw new RuntimeException("不正确的格式日期");
        }
        return endDate;
    }

    public Date getEndDate(Date date) {
        int year = this.getYear(date);
        int month = this.getMonth(date);
        int day = month == 12 ? 31 : 25;

        Date endDate;
        try {
            endDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            throw new RuntimeException("不正确的格式日期");
        }
        return endDate;
    }






}
