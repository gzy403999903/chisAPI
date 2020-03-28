package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.PaidAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PaidAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PaidAccount record);

    PaidAccount selectByPrimaryKey(Integer id);

    List<PaidAccount> selectAll();

    int updateByPrimaryKey(PaidAccount record);



    /* -------------------------------------------------------------------------------------------------------------- */

    void updateInvoiceNo(@Param("lshList") List<String> lshList,
                         @Param("invoiceNo") String invoiceNo);

    void updatePaymentNo(@Param("lshList") List<String> lshList,
                         @Param("paymentNo") String paymentNo,
                         @Param("payerId") Integer payerId,
                         @Param("payDate") Date payDate);

    void updateApproveStateByPaymentNo(@Param("paymentNo") String paymentNo,
                                       @Param("approverId") Integer approverId,
                                       @Param("approveDate") Date approveDate,
                                       @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectLshGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                           @Param("pemSupplierId") Integer pemSupplierId,
                                                           @Param("sysClinicId") Integer sysClinicId,
                                                           @Param("invoiceNo") String invoiceNo,
                                                           @Param("paymentNo") String paymentNo,
                                                           @Param("approveState") Byte approveState);

    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);

    List<Map<String, Object>> selectPaymentNoGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                                 @Param("pemSupplierId") Integer pemSupplierId,
                                                                 @Param("sysClinicId") Integer sysClinicId,
                                                                 @Param("approveState") Byte approveState,
                                                                 @Param("paymentNo") String paymentNo);

    List<Map<String, Object>> selectByPaymentNo(@Param("paymentNo") String paymentNo);


}
