package com.chisapp.modules.nurseworkstation.dao;

import com.chisapp.modules.nurseworkstation.bean.PaymentRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PaymentRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentRecord record);

    PaymentRecord selectByPrimaryKey(Integer id);

    List<PaymentRecord> selectAll();

    int updateByPrimaryKey(PaymentRecord record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<PaymentRecord> selectByLsh(@Param("lsh") String lsh);

    List<Map<String, Object>> selectClinicListByCriteria(@Param("creationDate") String[] creationDate,
                                                         @Param("sysClinicId") Integer sysClinicId,
                                                         @Param("lsh") String lsh,
                                                         @Param("creatorName") String creatorName);

    Map<String, Object> selectClinicGroupByLsh(@Param("sysClinicId") Integer sysClinicId,
                                               @Param("lsh") String lsh);

}
