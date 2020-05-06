package com.chisapp.modules.inventory.dao;

import com.chisapp.modules.inventory.bean.InventorySubtract;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InventorySubtractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InventorySubtract record);

    InventorySubtract selectByPrimaryKey(Integer id);

    List<InventorySubtract> selectAll();

    int updateByPrimaryKey(InventorySubtract record);



    /* -------------------------------------------------------------------------------------------------------------- */
    int updateApproveStateByLsh(@Param("approverId") Integer approverId,
                                @Param("approveDate") Date approvedate,
                                @Param("approveState") Byte approveState,
                                @Param("lsh") String lsh);

    List<Map<String, Object>> selectClinicListByCriteria(@Param("creationDate") String[] creationDate,
                                                         @Param("sysClinicId") Integer sysClinicId,
                                                         @Param("approveState") Byte approveState,
                                                         @Param("pemSupplierName") String pemSupplierName,
                                                         @Param("gsmGoodsOid") String gsmGoodsOid,
                                                         @Param("gsmGoodsName") String gsmGoodsName);

    List<Map<String, Object>> selectClinicLshGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                 @Param("sysClinicId") Integer sysClinicId,
                                                                 @Param("approveState") Byte approveState,
                                                                 @Param("sysClinicName") String sysClinicName,
                                                                 @Param("pemSupplierName") String pemSupplierName);

    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);


}
