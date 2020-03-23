package com.chisapp.modules.purchase.dao;

import com.chisapp.modules.purchase.bean.Supplier;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SupplierMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Supplier record);

    Supplier selectByPrimaryKey(Integer id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier record);


    /*----------------------------------------------------------------------------------------------------------------*/

    void addArrearagesAmount(@Param("id") Integer id,
                             @Param("amount") BigDecimal amount);

    void subtractArrearagesAmount(@Param("id") Integer id,
                                  @Param("amount") BigDecimal amount);

    List<Map<String, Object>> selectByCriteria(@Param("oid") String oid,
                                               @Param("name") String name,
                                               @Param("contacterPhone") String contacterPhone,
                                               @Param("state") Boolean state,
                                               @Param("arrearagesAmount") BigDecimal arrearagesAmount,
                                               @Param("arrearagesLimit") BigDecimal arrearagesLimit,
                                               @Param("arrearagesDays") Integer arrearagesDays);

    List<Map<String, Object>> selectEnabledLikeByName(@Param("name") String name);
}
