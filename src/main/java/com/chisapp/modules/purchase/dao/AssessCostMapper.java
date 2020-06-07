package com.chisapp.modules.purchase.dao;

import com.chisapp.modules.purchase.bean.AssessCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AssessCostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssessCost record);

    AssessCost selectByPrimaryKey(Integer id);

    List<AssessCost> selectAll();

    int updateByPrimaryKey(AssessCost record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<AssessCost> selectBySupplierIdAndGoodsIdList(@Param("pemSupplierId") Integer pemSupplierId,
                                                      @Param("gsmGoodsIdList") List<Integer> gsmGoodsIdList);

    List<Map<String, Object>> selectByCriteria(@Param("pemSupplierOid") String pemSupplierOid,
                                               @Param("pemSupplierName") String pemSupplierName,
                                               @Param("gsmGoodsOid") String gsmGoodsOid,
                                               @Param("gsmGoodsName") String gsmGoodsName);
}
