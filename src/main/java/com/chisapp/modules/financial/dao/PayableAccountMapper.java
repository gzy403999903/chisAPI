package com.chisapp.modules.financial.dao;

import com.chisapp.modules.financial.bean.PayableAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PayableAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayableAccount record);

    PayableAccount selectByPrimaryKey(Integer id);

    List<PayableAccount> selectAll();

    int updateByPrimaryKey(PayableAccount record);


    /* -------------------------------------------------------------------------------------------------------------- */

    List<Map<String, Object>> selectGroupListByCriteria(@Param("creationDate") String[] creationDate,
                                                        @Param("pemSupplierId") Integer pemSupplierId,
                                                        @Param("sysClinicId") Integer sysClinicId,
                                                        @Param("arrearagesAmount") BigDecimal arrearagesAmount);

    List<Map<String, Object>> selectByLsh(@Param("lsh") String lsh);

}
