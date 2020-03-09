package com.chisapp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 主键生成器
 *
 * @Author: Tandy
 * @Date: 2019/5/8 10:18
 * @Version 1.0
 */
public class KeyUtils {

    public static Integer UNIQUE_NO = 10;

    /**
     * 用于防止连续申请 key 时造成重复,
     * 在返回 key 后面加上调用此方法返回的数值
     * @return
     */
    private static Integer getUniqueNo () {
        if (UNIQUE_NO == 99) {
            UNIQUE_NO = 10;
        }

        return UNIQUE_NO++;
    }

    /**
     * 获取一个时间戳字符串
     * @return
     */
    private static String getTimestampStr () {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSSS");
        return formatter.format(new Date());
    }

    /**
     * 获取一个补位(五位数以下)的 ID 字符串,
     * @param id
     * @return
     */
    private static String getFillGapId(String id) {
        switch (id.length()) {
            case 1: id = "0000" + id; break;
            case 2: id = "000" + id; break;
            case 3: id = "00" + id; break;
            case 4: id = "0" + id; break;
            default: return id;
        }

        return id;
    }

    /**
     * 获取一个 UUID
     * 返回格式去除了 "-"
     *
     * @return
     */
    public static synchronized String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /**
     * 获取一个以 user id 开头以时间结尾的流水号
     * @return
     */
    public static synchronized String getLSH(Integer userId) {
        // 返回 LSH
        return "LSH" + getFillGapId(userId.toString()) + getUniqueNo() + getTimestampStr();
    }

    /**
     * 获取批次号
     * @return
     */
    public static synchronized String getPch() {
        int len = getTimestampStr().length();
        return  getTimestampStr().substring(8, len) + getUniqueNo();
    }
}
