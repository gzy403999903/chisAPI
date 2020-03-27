package com.chisapp.modules.datareport.service;

import com.chisapp.modules.datareport.bean.SellRecord;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/11 14:46
 * @Version 1.0
 */
public interface SellRecordService {
    /* --------------------------------------------------------------------------------------------------------------- */
    /**
     * 获取 Redis Hash Key
     * 组成结构为 'SellRecord:Clinic' + 用户机构ID
     * @return
     */
    default String getRedisHashKey() {
        String PREFIX = "SellRecord:Clinic";
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        return PREFIX + user.getSysClinicId();
    }

    /**
     * 将销售记录暂存到缓存
     * 存储结构为 key: getRedisHashKey, f: prescriptionLsh, v: recordJson
     * @param recordJson
     */
    void saveOrUpdateToCache(String recordJson);

    /**
     * 根据处方流水号删除对应的缓存记录
     * @param prescriptionLsh
     */
    void deleteByPrescriptionLshFromCache(String prescriptionLsh);

    /**
     * 获取对应机构 所有会员的汇总记录
     * @return
     */
    List<SellRecord> getClinicMemberGroupListFromCache();

    /**
     * 获取对应机构对应会员的所有明细记录
     * @return
     */
    List<SellRecord> getClinicListByMrmMemberIdFromCache(Integer mrmMemberId);

    /**
     * 获取对应机构 所有药品处方的汇总记录
     * @return
     */
    List<SellRecord> getClinicDrugsPrescriptionGroupListFromCache();

    /**
     * 根据处方流水号获取对应的销售记录明细
     * @return
     */
    List<SellRecord> getClinicListByPrescriptionLshFromCache(String prescriptionLsh);

    /**
     * 获取应机构 对应医生 对应会员的销售明细, 并计算出一些结果进行返回
     * [医生问诊: 合计处方数, 金额等]
     * @param sysDoctorId
     * @param mrmMemberId
     * @return
     */
    Map<String, Object> countPrescriptionByCriteriaFromCache(Integer sysDoctorId, Integer mrmMemberId);

    /* --------------------------------------------------------------------------------------------------------------- */
    /**
     * 保存销售记录集合
     * @param sellRecordList
     */
    @Transactional
    void saveList(List<SellRecord> sellRecordList);

    /**
     * 更新退费状态
     * @param idList
     */
    @Transactional
    void updateReturnedByIdList(List<Integer> idList);

    /**
     * 更新发票内容
     * @param idList
     * @param invoiceTypeId
     * @param invoiceNo
     */
    void updateInvoiceByIdList(List<Integer> idList, Integer invoiceTypeId, String invoiceNo);

    /**
     * 根据条件获取对应的销售汇总记录
     * @param sysClinicId
     * @param creationDate
     * @param lsh
     * @param mrmMemberName
     * @return
     */
    List<Map<String, Object>> getClinicGroupListByCriteria(Integer sysClinicId, String[] creationDate, String lsh, String mrmMemberName);

    /**
     * 根据流水号获取机构未退费的销售明细
     * @param sysClinicId
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getClinicUnreturnedListByLsh(Integer sysClinicId, String lsh);

    /**
     * 根据流水号获取机构销售明细
     * @param sysClinicId
     * @param lsh
     * @return
     */
    List<Map<String, Object>> getClinicListByLsh(Integer sysClinicId, String lsh);

   /* --------------------------------------------------------------------------------------------------------------- */
    /**
     * 排序
     * @return
     */
    default List<SellRecord> sellRecordListSort(List<SellRecord> recordList) {
        recordList.sort(new Comparator<SellRecord>() {
            @Override
            public int compare(SellRecord record1, SellRecord record2) {
                return record1.getDwtSellPrescriptionDate().compareTo(record2.getDwtSellPrescriptionDate());
            }
        });
        return recordList;
    }

}
