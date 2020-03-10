package com.chisapp.modules.financial.service;

import com.chisapp.modules.financial.bean.PayableAccount;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/16 19:55
 * @Version 1.0
 */
public interface PayableAccountService {

    /**
     * 保存操作
     * @param payableAccountList
     */
    @Transactional
    void saveList(List<PayableAccount> payableAccountList);

    /**
     * 根据查询条件获取应付账款汇总
     * @param creationDate
     * @param pemSupplierId
     * @param sysClinicId
     * @param arrearagesAmount
     * @return
     */
    List<Map<String, Object>> getGroupListByCriteria(String[] creationDate,
                                                     Integer pemSupplierId,
                                                     Integer sysClinicId,
                                                     BigDecimal arrearagesAmount);

    /**
     * 根据 lsh 获取对应的应付账款明细
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getByLsh(String lsh);
}
