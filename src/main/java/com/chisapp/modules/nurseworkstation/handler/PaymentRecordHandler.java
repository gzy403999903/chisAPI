package com.chisapp.modules.nurseworkstation.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.nurseworkstation.service.PaymentRecordService;
import com.chisapp.modules.system.bean.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/24 12:04
 * @Version 1.0
 */
@RequestMapping("/paymentRecord")
@RestController
public class PaymentRecordHandler {

    private PaymentRecordService paymentRecordService;
    @Autowired
    public void setPaymentRecordService(PaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    /**
     * 根据流水号获取对应的付款记录
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh (@RequestParam String lsh) {
        return PageResult.success().resultSet("list", this.paymentRecordService.getByLsh(lsh));
    }

    /**
     * 获取机构收费明细
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param lsh
     * @param creatorName
     * @return
     */
    @GetMapping("/getClinicListByCriteria")
    public PageResult getClinicListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
            @RequestParam(required = false) String lsh,
            @RequestParam(required = false) String creatorName){

        // 获取用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordService.getClinicListByCriteria(creationDate, user.getSysClinicId(), lsh, creatorName);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }





}
