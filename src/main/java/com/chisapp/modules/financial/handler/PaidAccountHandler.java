package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.common.utils.JSONUtils;
import com.chisapp.modules.financial.bean.PaidAccount;
import com.chisapp.modules.financial.service.PaidAccountService;
import com.chisapp.modules.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/17 11:48
 * @Version 1.0
 */
@RequestMapping("/paidAccount")
@RestController
public class PaidAccountHandler {
    private PaidAccountService paidAccountService;

    @Autowired
    public void setPaidAccountService(PaidAccountService paidAccountService) {
        this.paidAccountService = paidAccountService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 保存操作
     * @param mapJson
     * @return
     */
    @PostMapping("/save")
    public PageResult save(@RequestBody String mapJson) {
        Map<String, String> map = JSONUtils.parseJsonToObject(mapJson, new TypeReference<Map<String, String>>() {});
        if (map.get("paidAccountListJson") == null) {
            return  PageResult.fail().msg("缺少请求参数");
        }

        String paidAccountListJson =  map.get("paidAccountListJson");
        List<PaidAccount> paidAccountList = JSONUtils.parseJsonToObject(paidAccountListJson, new TypeReference<List<PaidAccount>>() {});
        paidAccountService.saveList(paidAccountList);
       return PageResult.success();
    }

    /**
     * 录入发票号
     * @param mapJson
     * @return
     */
    @PutMapping("/updateInvoiceNo")
    public PageResult updateInvoiceNo(@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson,new TypeReference<Map<String, Object>>() {});
        String lshListJson;
        String invoiceNo;
        try {
            lshListJson = map.get("lshListJson").toString();
            invoiceNo = map.get("invoiceNo").toString();
        } catch (NullPointerException e) {
            return PageResult.fail().msg("缺少请求参数");
        }

        List<String> lshList = JSONUtils.parseJsonToObject(lshListJson, new TypeReference<List<String>>() {});
        paidAccountService.updateInvoiceNo(lshList, invoiceNo);
        return PageResult.success();
    }

    /**
     * 录入凭证号
     * @param mapJson
     * @return
     */
    @PutMapping("/updatePaymentNo")
    public PageResult updatePaymentNo(@RequestBody String mapJson) {
        Map<String, Object> map = JSONUtils.parseJsonToObject(mapJson,new TypeReference<Map<String, Object>>() {});
        String lshListJson;
        String paymentNo;
        try {
            lshListJson = map.get("lshListJson").toString();
            paymentNo = map.get("paymentNo").toString();
        } catch (NullPointerException e) {
            return PageResult.fail().msg("缺少请求参数");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<String> lshList = JSONUtils.parseJsonToObject(lshListJson, new TypeReference<List<String>>() {});
        paidAccountService.updatePaymentNo(lshList, paymentNo, user.getId(), new Date());
        return PageResult.success();
    }

    /**
     * 驳回操作
     * @param paymentNo
     * @return
     */
    @PutMapping("/unapproved")
    public PageResult unapproved(String paymentNo) {
        paidAccountService.unapproved(paymentNo);
        return PageResult.success();
    }

    /**
     * 通过操作
     * @param paymentNo
     * @return
     */
    @PutMapping("/approved")
    public PageResult approved(String paymentNo) {
        paidAccountService.approved(paymentNo);
        return PageResult.success();
    }

    /**
     * 根据条件获取已付汇总
     * [预付明细调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param approveState
     * @return
     */
    @GetMapping("/getLshGroupListByCriteria")
    public PageResult getLshGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Integer pemSupplierId,
            @RequestParam(required = false) Integer sysClinicId,
            @RequestParam(required = false) Byte approveState){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = paidAccountService.getLshGroupListByCriteria(creationDate, pemSupplierId, sysClinicId, approveState);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据 流水号 获取对应付款明细
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh(@RequestParam String lsh) {
        List<Map<String, Object>> list = paidAccountService.getByLsh(lsh);
        return PageResult.success().resultSet("list", list);
    }

    /**
     * 根据条件获取已付汇总
     * [付款单审批调用]
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param approveState
     * @return
     */
    @GetMapping("/getPaymentNoGroupListByCriteria")
    public PageResult getPaymentNoGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Integer pemSupplierId,
            @RequestParam(required = false) Integer sysClinicId,
            @RequestParam(required = false) Byte approveState,
            @RequestParam(required = false) String paymentNo){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = paidAccountService.getPaymentNoGroupListByCriteria(creationDate, pemSupplierId, sysClinicId, approveState, paymentNo);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据 凭证号 获取对应付款明细
     * @param paymentNo
     * @return
     */
    @GetMapping("/getByPaymentNo")
    public PageResult getByPaymentNo(@RequestParam String paymentNo) {
        List<Map<String, Object>> list = paidAccountService.getByPaymentNo(paymentNo);
        return PageResult.success().resultSet("list", list);
    }

}
