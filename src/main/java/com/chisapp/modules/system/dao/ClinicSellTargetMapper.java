package com.chisapp.modules.system.dao;

import com.chisapp.modules.system.bean.ClinicSellTarget;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClinicSellTargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClinicSellTarget record);

    ClinicSellTarget selectByPrimaryKey(Integer id);

    List<ClinicSellTarget> selectAll();

    int updateByPrimaryKey(ClinicSellTarget record);

    List<Map<String, Object>> selectByCriteria(@Param("sysClinicName") String sysClinicName);

}
