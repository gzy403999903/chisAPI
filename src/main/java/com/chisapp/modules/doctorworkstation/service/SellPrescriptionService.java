package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.SellPrescription;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/9 16:07
 * @Version 1.0
 */
@CacheConfig(cacheNames = "SellPrescription")
public interface SellPrescriptionService {

    /**
     * 获取 Redis Hash Key
     * 组成结构为 'SellPrescription:Clinic' + 用户机构ID
     * @return
     */
    default String getRedisHashKey() {
        String PREFIX = "SellPrescription:Clinic";
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        return PREFIX + user.getSysClinicId();
    }

    /**
     * 将处方暂存到缓存
     * 储存结构为  Key:getRedisHashKey , f:lsh, v:prescriptionJson
     * @param prescriptionJson
     */
    void saveOrUpdateToCache(String prescriptionJson);

    /**
     * 根据 LSH 删除缓存中对应的处方
     * @param lsh
     */
    void deleteByLshFromCache(String lsh);

    /**
     * 从缓存中获取对应机构对应医生对应类型的处方
     * @param sysDoctorId
     * @param mrmMemberId
     * @param sysSellTypeId
     * @param entityTypeId
     * @return
     */
    List<SellPrescription> getClinicDoctorGroupListByCriteriaFromCache(Integer sysDoctorId,
                                                                       Integer mrmMemberId,
                                                                       Integer sysSellTypeId,
                                                                       Integer entityTypeId);

    /**
     * 根据流水号获取对应的处方内容
     * @param lsh
     * @return
     */
    List<SellPrescription> getByLshFromCache(String lsh);

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 保存操作
     * @param sellPrescriptionList
     */
    @Transactional
    void saveList(List<SellPrescription> sellPrescriptionList);

    /**
     * 根据病例 ID 获取对应的处方集合
     * @param dwtClinicalHistoryId
     * @return
     */
    List<SellPrescription> getByClinicalHistoryId(Integer dwtClinicalHistoryId);

    /**
     * 根据条件获取对应处方汇总
     * @param creationDate
     * @param lsh
     * @param sysClinicId
     * @param sysSellTypeId
     * @param entityTypeId
     * @param mrmMemberId
     * @param sysDoctorName
     * @return
     */
    List<Map<String, Object>> getGroupListByCriteria(String[] creationDate, // 处方日期
                                                     String lsh, // 流水号
                                                     Integer sysClinicId, // 机构ID
                                                     Integer sysSellTypeId, // 销售类型 [商品、项目]
                                                     Integer entityTypeId, // 销售实体类型 [西药、中药、卫生材料]
                                                     Integer mrmMemberId, // 会员ID
                                                     String sysDoctorName); // 处方医生姓名

    /**
     * 根据流水号获取对应机构的处方明细
     * @param lsh
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getClinicListByLsh(String lsh, Integer sysClinicId);

    /**
     * 根据流水号获取对应处方明细
     * [用于医生调用历史处方]
     * @param lsh
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getByLshForTemplate(String lsh,
                                                  Integer sysClinicId); // 当前操作人机构ID (用于关联库存)

    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * 设置会员部分属性
     * @param sellPrescription
     * @param memberMap
     */
    default void setMemberInfo(SellPrescription sellPrescription, Map<String, Object> memberMap) {
        String mrmMemberOid = memberMap.get("oid").toString();
        String mrmMemberName = memberMap.get("name").toString();
        String genderName = memberMap.get("genderName").toString();
        String phone = memberMap.get("phone").toString();
        Float memberDiscountRate = Float.parseFloat(memberMap.get("discountRate").toString());

        sellPrescription.setMrmMemberOid(mrmMemberOid);
        sellPrescription.setMrmMemberName(mrmMemberName);
        sellPrescription.setGenderName(genderName);
        sellPrescription.setPhone(phone);
        sellPrescription.setMemberDiscountRate(memberDiscountRate);

    }

}
