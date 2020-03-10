package com.chisapp.modules.nurseworkstation.service;

import com.chisapp.modules.nurseworkstation.bean.RegistrationRecord;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/12/16 12:40
 * @Version 1.0
 */
public interface RegistrationRecordService {
    /**
     * 获取 Redis Hash Key
     * 组成结构为 'RegistrationRecord:Clinic' + 用户机构ID
     * @return
     */
    default String getRedisHashKey() {
        String PREFIX = "RegistrationRecord:Clinic";
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息
        return PREFIX + user.getSysClinicId();
    }

    /**
     * 将挂号记录暂存到缓存
     * 存储结构为 key: getRedisHashKey, f: lsh, v: recordJson
     * @param registrationRecord
     */
    void saveOrUpdateToCache(RegistrationRecord registrationRecord);

    /**
     * 根据流水号删除缓存中对应的挂号记录
     * @param lsh
     */
    void deleteByLshFromCache(String lsh);

    /**
     * 根据流水号获取一个对应的挂号记录
     * @param lsh
     * @return
     */
    RegistrationRecord getByLshFromCache(String lsh);

    /**
     * 从缓存中获取所有挂号记录
     * @return
     */
    List<RegistrationRecord> getClinicListFromCache();

    /**
     * 从缓存中获取对应医生的挂号记录
     * @param doctorId
     * @return
     */
    List<RegistrationRecord> getClinicListByDoctorIdFromCache(Integer doctorId);
    /*----------------------------------------------------------------------------------------------------------------*/
    /**
     * 保存操作
     * @param registrationRecord
     */
    @Transactional
    void save(RegistrationRecord registrationRecord);

    /**
     * 根据查询条件获取对象集合
     * @param sysClinicId
     * @param creationDate
     * @param mrmMemberName
     * @param cimItemName
     * @param sysDoctorName
     * @return
     */
    List<Map<String, Object>> getClinicListByCriteria(Integer sysClinicId,
                                                      String[] creationDate,
                                                      String mrmMemberName,
                                                      String cimItemName,
                                                      String sysDoctorName);

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * 排序
     * @return
     */
    default List<RegistrationRecord> registrationRecordListSort(List<RegistrationRecord> recordList) {
        recordList.sort(new Comparator<RegistrationRecord>() {
            @Override
            public int compare(RegistrationRecord record1, RegistrationRecord record2) {
                return record1.getCreationDate().compareTo(record2.getCreationDate());
            }
        });
        return recordList;
    }

}
