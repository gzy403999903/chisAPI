package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.InventoryAllot;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InventoryAllotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventoryAllot record);

    InventoryAllot selectByPrimaryKey(Integer id);

    List<InventoryAllot> selectAll();

    int updateByPrimaryKey(InventoryAllot record);


    /* -------------------------------------------------------------------------------------------------------------- */

    int updateApproveStateByLsh(@Param("approverId") Integer approverId,
                                @Param("approveDate") Date approvedate,
                                @Param("approveState") Byte approveState,
                                @Param("lsh") String lsh);

    List<Map<String, Object>> selectClinicListByCriteria(@Param("creationDate") String[] creationDate,
                                                         @Param("sysClinicId") Integer sysClinicId,
                                                         @Param("approveState") Byte approveState,
                                                         @Param("gsmGoodsName") String gsmGoodsName);

    List<Map<String, Object>> selectClinicLshGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                 @Param("sysClinicId") Integer sysClinicId,
                                                                 @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectByLsh(String lsh);
}
