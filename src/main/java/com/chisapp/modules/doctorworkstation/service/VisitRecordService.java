package com.chisapp.modules.doctorworkstation.service;

import com.chisapp.modules.doctorworkstation.bean.VisitRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/1/16 21:56
 * @Version 1.0
 */
public interface VisitRecordService {

    /**
     * 保存操作
     * @param visitRecord
     */
    @Transactional
    void save(VisitRecord visitRecord);

    /**
     * 更新操作
     * @param visitRecord
     * @param nextAppointmentDate
     */
    @Transactional
    void update(VisitRecord visitRecord, Date nextAppointmentDate);

    /**
     * 根据 id 获取对应的回访记录
     * @param id
     * @return
     */
    VisitRecord getById(Integer id);

    /**
     * 根据病例ID 获取未完成的回访记录
     * @param dwtClinicalHistoryId
     * @return
     */
    List<VisitRecord> getUnfinishedByDwtClinicalHistoryId(Integer dwtClinicalHistoryId);

    /**
     * 根据条件获取对应的回访记录
     * @param sysClinicId
     * @param creatorId
     * @param appointmentDate
     * @param mrmMemberName
     * @param finished
     * @return
     */
    List<Map<String, Object>> getByCriteria(Integer sysClinicId,
                                            Integer creatorId,
                                            String[] appointmentDate,
                                            String mrmMemberName,
                                            Boolean finished);
}
