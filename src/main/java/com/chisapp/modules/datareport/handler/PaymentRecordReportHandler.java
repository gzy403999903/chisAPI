package com.chisapp.modules.datareport.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.datareport.service.PaymentRecordReportService;
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
 * @Date: 2020-03-15 20:04
 * @Version 1.0
 */
@RequestMapping("/paymentRecordReport")
@RestController
public class PaymentRecordReportHandler {

    private PaymentRecordReportService paymentRecordReportService;
    @Autowired
    public void setPaymentRecordReportService(PaymentRecordReportService paymentRecordReportService) {
        this.paymentRecordReportService = paymentRecordReportService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 获取全机构 收费方式汇总(按人员)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param creatorName
     * @return
     */
    @GetMapping("/getPaymentRecordListByCriteria")
    public PageResult getPaymentRecordListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                     @RequestParam(defaultValue="1") Integer pageSize,
                                                     @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                     @RequestParam(required = false) String sysClinicName,
                                                     @RequestParam(required = false) String creatorName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getPaymentRecordListByCriteria(creationDate, null, sysClinicName, creatorName, "creatorId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 收费方式汇总(按人员)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicPaymentRecordListByCriteria")
    public PageResult getClinicPaymentRecordListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                           @RequestParam(defaultValue="1") Integer pageSize,
                                                           @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                           @RequestParam(required = false) String creatorName) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getPaymentRecordListByCriteria(creationDate, user.getSysClinicId(), null, creatorName, "creatorId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 收费方式汇总(按门店)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @return
     */
    @GetMapping("/getPaymentRecordListByCriteria2")
    public PageResult getPaymentRecordListByCriteria2(@RequestParam(defaultValue="1") Integer pageNum,
                                                      @RequestParam(defaultValue="1") Integer pageSize,
                                                      @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                      @RequestParam(required = false) String sysClinicName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getPaymentRecordListByCriteria(creationDate, null, sysClinicName, null, "sysClinicId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取当前机构 收费方式汇总(按门店)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicPaymentRecordListByCriteria2")
    public PageResult getClinicPaymentRecordListByCriteria2(@RequestParam(defaultValue="1") Integer pageNum,
                                                            @RequestParam(defaultValue="1") Integer pageSize,
                                                            @RequestParam(value = "creationDate[]",required = false) String[] creationDate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getPaymentRecordListByCriteria(creationDate, user.getSysClinicId(), null, null, "sysClinicId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 储值方式汇总(按人员)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param creatorName
     * @return
     */
    @GetMapping("/getDepositPaymentRecordListByCriteria")
    public PageResult getDepositPaymentRecordListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                            @RequestParam(defaultValue="1") Integer pageSize,
                                                            @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                            @RequestParam(required = false) String sysClinicName,
                                                            @RequestParam(required = false) String creatorName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getDepositPaymentRecordListByCriteria(creationDate, null, sysClinicName, creatorName, "creatorId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }


    /**
     * 获取当前机构 储值方式汇总(按人员)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicDepositPaymentRecordListByCriteria")
    public PageResult getClinicDepositPaymentRecordListByCriteria(@RequestParam(defaultValue="1") Integer pageNum,
                                                                  @RequestParam(defaultValue="1") Integer pageSize,
                                                                  @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                                  @RequestParam(required = false) String creatorName) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getDepositPaymentRecordListByCriteria(creationDate, user.getSysClinicId(), null, creatorName, "creatorId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 获取全机构 储值方式汇总(按门店)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param sysClinicName
     * @param creatorName
     * @return
     */
    @GetMapping("/getDepositPaymentRecordListByCriteria2")
    public PageResult getDepositPaymentRecordListByCriteria2(@RequestParam(defaultValue="1") Integer pageNum,
                                                             @RequestParam(defaultValue="1") Integer pageSize,
                                                             @RequestParam(value = "creationDate[]",required = false) String[] creationDate,
                                                             @RequestParam(required = false) String sysClinicName) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getDepositPaymentRecordListByCriteria(creationDate, null, sysClinicName, null, "sysClinicId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }


    /**
     * 获取当前机构 储值方式汇总(按门店)
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @return
     */
    @GetMapping("/getClinicDepositPaymentRecordListByCriteria2")
    public PageResult getClinicDepositPaymentRecordListByCriteria2(@RequestParam(defaultValue="1") Integer pageNum,
                                                                   @RequestParam(defaultValue="1") Integer pageSize,
                                                                   @RequestParam(value = "creationDate[]",required = false) String[] creationDate) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList =
                paymentRecordReportService.getDepositPaymentRecordListByCriteria(creationDate, user.getSysClinicId(), null, null, "sysClinicId");
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);

        return PageResult.success().resultSet("page", pageInfo);
    }


}
