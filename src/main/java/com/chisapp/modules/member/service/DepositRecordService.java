package com.chisapp.modules.member.service;

import com.chisapp.modules.member.bean.DepositRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/31 11:41
 * @Version 1.0
 */
public interface DepositRecordService {

    /**
     * 保存一条储值记录
     * @param mrmMemberId
     * @param paymentRecordJson
     */
    @Transactional
    void save(Integer mrmMemberId, String paymentRecordJson);

    /**
     * 根据流水号进行退回
     * @param lsh
     * @param lsh
     */
    @Transactional
    void saveForReturned(String lsh);

    /**
     * 根据流水号后去对应的储值记录
     * @param lsh
     * @return
     */
    List<DepositRecord> getByLsh(String lsh);

    /**
     * 根据查询条件获取储值记录集合
     * @param sysClinicId
     * @param creationDate
     * @param mrmMemberName
     * @param returned
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer sysClinicId, String[] creationDate, String lsh, String mrmMemberName, Boolean returned);


}
