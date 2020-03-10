package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.InventoryAdd;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InventoryAddMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventoryAdd record);

    InventoryAdd selectByPrimaryKey(Integer id);

    List<InventoryAdd> selectAll();

    int updateByPrimaryKey(InventoryAdd record);


    /* -------------------------------------------------------------------------------------------------------------- */

    int updateApproveStateByLsh(@Param("approverId") Integer approverId,
                                @Param("approveDate") Date approvedate,
                                @Param("updateApproveState") Byte updateApproveState,
                                @Param("lsh") String lsh,
                                @Param("currentApproveState") Byte currentApproveState);

    List<Map<String, Object>> selectClinicListByCriteria(@Param("creationDate") String[] creationDate,
                                                         @Param("sysClinicId") Integer sysClinicId,
                                                         @Param("approveState") Byte approveState,
                                                         @Param("actionType") Byte actionType,
                                                         @Param("pemSupplierName") String pemSupplierName);

    List<Map<String, Object>> selectClinicLshGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                 @Param("sysClinicId") Integer sysClinicId,
                                                                 @Param("approveState") Byte approveState,
                                                                 @Param("pemSupplierName") String pemSupplierName);

    List<Map<String, Object>> selectByLsh(String lsh);
}
