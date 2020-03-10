package com.chisapp.modules.purchase.dao;

import com.chisapp.modules.purchase.bean.PurchaseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PurchaseOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(Integer id);

    List<PurchaseOrder> selectAll();

    int updateByPrimaryKey(PurchaseOrder record);

    /* -------------------------------------------------------------------------------------------------------------- */

    int updateApproveStateByLsh(@Param("approverId") Integer approverId,
                                @Param("approveDate") Date approvedate,
                                @Param("updateApproveState") Byte updateApproveState,
                                @Param("lsh") String lsh,
                                @Param("currentApproveState") Byte currentApproveState);

    void updateInventoryStateByCriteria(@Param("inventoryState") Boolean inventoryState,
                                        @Param("lsh") String lsh,
                                        @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectLshGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                           @Param("approveState") Byte approveState,
                                                           @Param("lsh") String lsh,
                                                           @Param("pemSupplierName") String pemSupplierName);

    List<Map<String, Object>> selectGoodsGroupListByLsh(String lsh);

    List<Map<String, Object>> selectClinicLshGroupListByInventoryState(@Param("sysClinicId") Integer sysClinicId,
                                                                       @Param("inventoryState") Boolean inventoryState);

    List<Map<String, Object>> selectClinicListByLsh(@Param("lsh") String lsh,
                                                    @Param("sysClinicId") Integer sysClinicId);

    List<Map<String, Object>> selectTrackListByLsh(String lsh);

    List<PurchaseOrder> selectByLsh(@Param("lsh") String lsh);

}
