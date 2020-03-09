package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.PaymentWay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentWayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentWay record);

    PaymentWay selectByPrimaryKey(Integer id);

    List<PaymentWay> selectAll();

    int updateByPrimaryKey(PaymentWay record);

    /* -------------------------------------------------------------------------------------------------------------- */

    List<PaymentWay> selectByCriteria(@Param("name") String name, @Param("state") Boolean state);

    List<PaymentWay> selectEnabled();
}
