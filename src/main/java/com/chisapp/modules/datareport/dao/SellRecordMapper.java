package com.chisapp.modules.datareport.dao;

import com.chisapp.modules.datareport.bean.SellRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SellRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SellRecord record);

    SellRecord selectByPrimaryKey(Integer id);

    List<SellRecord> selectAll();

    int updateByPrimaryKey(SellRecord record);



    /* -------------------------------------------------------------------------------------------------------------- */

    void updateReturnedByIdList(@Param("idList") List<Integer> idList);

    void updateInvoiceByIdList(@Param("idList") List<Integer> idList,
                               @Param("invoiceTypeId") Integer invoiceTypeId,
                               @Param("invoiceNo") String invoiceNo,
                               @Param("invoiceDate") Date invoiceDate);

    List<Map<String, Object>> selectClinicGroupListByCriteria(@Param("sysClinicId") Integer sysClinicId,
                                                              @Param("creationDate") String[] creationDate,
                                                              @Param("lsh") String lsh,
                                                              @Param("mrmMemberName") String mrmMemberName);

    List<Map<String, Object>> selectClinicUnreturnedListByLsh(@Param("sysClinicId") Integer sysClinicId,
                                                              @Param("lsh") String lsh);

    List<Map<String, Object>> selectClinicListByLsh(@Param("sysClinicId") Integer sysClinicId,
                                                    @Param("lsh") String lsh);



}
