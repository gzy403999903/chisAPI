package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.SelfUsedRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SelfUsedRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SelfUsedRecord record);

    SelfUsedRecord selectByPrimaryKey(Integer id);

    List<SelfUsedRecord> selectAll();

    int updateByPrimaryKey(SelfUsedRecord record);


    /*----------------------------------------------------------------------------------------------------------------*/
    void updateApproveState(@Param("approverId") Integer approverId,
                            @Param("approveDate") Date approveDate,
                            @Param("approveState") Byte approveState,
                            @Param("lsh") String lsh);

    List<Map<String, Object>> selectByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                               @Param("creationDate") String[] creationDate,
                                               @Param("lsh") String lsh,
                                               @Param("sysClinicName") String sysClinicName,
                                               @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectClinicGroupListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                              @Param("creationDate") String[] creationDate,
                                                              @Param("lsh") String lsh,
                                                              @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);


}
