package com.chisapp.modules.member.dao;

import com.chisapp.modules.member.bean.DepositRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DepositRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepositRecord record);

    DepositRecord selectByPrimaryKey(Integer id);

    List<DepositRecord> selectAll();

    int updateByPrimaryKey(DepositRecord record);


    /******************************************************************************************************************/

    List<DepositRecord> selectByLsh(@Param("lsh") String lsh);

    List<Map<String, Object>> selectByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                               @Param("creationDate") String[] creationDate,
                                               @Param("lsh") String lsh,
                                               @Param("mrmMemberName") String mrmMemberName,
                                               @Param("returned") Boolean returned);

}
