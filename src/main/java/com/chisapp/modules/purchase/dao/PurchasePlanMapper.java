package com.chisapp.modules.purchase.dao;

import com.chisapp.modules.purchase.bean.PurchasePlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PurchasePlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchasePlan record);

    PurchasePlan selectByPrimaryKey(Integer id);

    List<PurchasePlan> selectAll();

    int updateByPrimaryKey(PurchasePlan record);


    /*---------------------------------------------------------------------------------------------------------------*/

    void updateApproveStateByPlanIdList(@Param("approveState") Byte approveState,
                                        @Param("approverId") Integer approverId,
                                        @Param("approveDate") Date approveDate,
                                        @Param("planList") List<PurchasePlan> planList);

    List<Map<String, Object>> selectByCriteria(@Param("creationDate") String[] creationDate,
                                               @Param("approveDate") String[] approveDate,
                                               @Param("approverId") Integer approverId,
                                               @Param("approveState") Byte approveState,
                                               @Param("sysClinicId") Integer sysClinicId,
                                               @Param("sysClinicName") String sysClinicName,
                                               @Param("gsmGoodsName") String gsmGoodsName);

    List<Map<String, Object>> selectViewByPlanIdList(@Param("planIdList") List<Integer> planIdList);

    List<PurchasePlan> selectByPlanIdList(@Param("planIdList") List<Integer> planIdList);


}
