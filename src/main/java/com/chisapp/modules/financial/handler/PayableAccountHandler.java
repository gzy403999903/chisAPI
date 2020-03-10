package com.chisapp.modules.financial.handler;

import com.chisapp.common.component.PageResult;
import com.chisapp.modules.financial.service.PayableAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/16 21:21
 * @Version 1.0
 */
@RequestMapping("/payableAccount")
@RestController
public class PayableAccountHandler {

    private PayableAccountService payableAccountService;

    @Autowired
    public void setPayableAccountService(PayableAccountService payableAccountService) {
        this.payableAccountService = payableAccountService;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 根据条件获取对应的应付账款汇总
     * @param pageNum
     * @param pageSize
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param arrearagesAmount
     * @return
     */
    @GetMapping("/getGroupListByCriteria")
    public PageResult getGroupListByCriteria (
            @RequestParam(defaultValue="1") Integer pageNum,
            @RequestParam(defaultValue="1") Integer pageSize,
            @RequestParam(value = "creationDate[]",required = false) String[] creationDate, // 创建日期
            @RequestParam(required = false) Integer pemSupplierId,
            @RequestParam(required = false) Integer sysClinicId,
            @RequestParam(required = false) BigDecimal arrearagesAmount){

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> pageList = payableAccountService.getGroupListByCriteria(creationDate, pemSupplierId, sysClinicId, arrearagesAmount);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(pageList);
        return PageResult.success().resultSet("page", pageInfo);
    }

    /**
     * 根据 lsh 获取对应的应付账款明细
     * @param lsh
     * @return
     */
    @GetMapping("/getByLsh")
    public PageResult getByLsh(String lsh) {
        List<Map<String, Object>> list = payableAccountService.getByLsh(lsh);
        return PageResult.success().resultSet("list", list);
    }

}
