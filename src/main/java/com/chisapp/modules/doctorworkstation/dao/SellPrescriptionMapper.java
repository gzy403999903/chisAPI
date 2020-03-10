package com.chisapp.modules.doctorworkstation.dao;

import com.chisapp.modules.doctorworkstation.bean.SellPrescription;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SellPrescriptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SellPrescription record);

    SellPrescription selectByPrimaryKey(Integer id);

    List<SellPrescription> selectAll();

    int updateByPrimaryKey(SellPrescription record);


    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectGroupListByCriteria(@Param("creationDate") String[] creationDate, // 处方日期
                                                        @Param("lsh") String lsh, // 流水号
                                                        @Param("sysClinicId") Integer sysClinicId, // 机构ID
                                                        @Param("sysSellTypeId") Integer sysSellTypeId, // 销售类型 [商品、项目]
                                                        @Param("entityTypeId") Integer entityTypeId, // 销售实体类型 [西药、中药、卫生材料]
                                                        @Param("mrmMemberId") Integer mrmMemberId, // 会员ID
                                                        @Param("sysDoctorName") String sysDoctorName); // 处方医生姓名

    List<Map<String, Object>> selectClinicListByLsh(@Param("lsh") String lsh,
                                                    @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectByLshForTemplate(@Param("lsh") String lsh,
                                                     @Param("sysClinicId") Integer sysClinicId);

    List<SellPrescription> selectByClinicalHistoryId(@Param("dwtClinicalHistoryId") Integer dwtClinicalHistoryId);
}
