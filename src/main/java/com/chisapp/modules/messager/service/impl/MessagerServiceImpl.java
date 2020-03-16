package com.chisapp.modules.messager.service.impl;

import com.chisapp.modules.datareport.service.InventoryReportService;
import com.chisapp.modules.messager.ben.Messager;
import com.chisapp.modules.messager.service.MessagerService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2020-03-16 21:58
 * @Version 1.0
 */
@Service
public class MessagerServiceImpl implements MessagerService {

    private InventoryReportService inventoryReportService;
    @Autowired
    public void setInventoryReportService(InventoryReportService inventoryReportService) {
        this.inventoryReportService = inventoryReportService;
    }

    /**
     * 内部枚举类
     */
    private enum typeEnum {
        INFO("info"), WARNING("warning");

        private typeEnum(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Messager> getMessageList() {
        List<Messager> messageList = new ArrayList<>();
        this.countExpiryDateList(messageList);

        return messageList;
    }

    /******************************************************************************************************************
        "id"	"name"
        "1"	    "系统维护"
        "2"	    "总经办"
        "3"	    "财务"
        "4"	    "采购"
        "5"	    "运营"
        "6"	    "质管"
        "7"	    "店长"
        "8"	    "全科医生"
        "9"	    "西医"
        "10"	"中医"
        "11"	"理疗师"
        "12"	"护士"
     ******************************************************************************************************************/

    /**
     * 返回近效期品种数量消息
     * @param messageList
     */
    private void countExpiryDateList(List<Messager> messageList) {
        int sums = 0;
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        int[] targetRoleIds = new int[]{7 ,12}; // 设置门店返回消息的分组ID
        for (int targetRoleId : targetRoleIds) { // 判断该用户是否属于该分组
            if (user.getSysRoleId() == targetRoleId) {
                sums = inventoryReportService.countExpiryDateListByCriteria(user.getSysClinicId(), 90);
                break;
            }
        }

        targetRoleIds = new int[]{5 ,6}; // 设置总部返回消息的分组ID
        for (int targetRoleId : targetRoleIds) { // 判断该用户是否属于该分组
            if (user.getSysRoleId() == targetRoleId) {
                sums = inventoryReportService.countExpiryDateListByCriteria(null, 90);
                break;
            }
        }

        if (sums <= 0) { // 如果没有返回结果则不继续执行
            return;
        }

        Messager messager = new Messager();
        messager.setType(typeEnum.WARNING.getName());
        messager.setContent("您有" + sums + "个90天内近效期商品未处理</br>请在以下位置查询</br>数据报表 --- 运营报表 --- 效期预警");
        messager.setSender("系统");

        messageList.add(messager);
    }



}
