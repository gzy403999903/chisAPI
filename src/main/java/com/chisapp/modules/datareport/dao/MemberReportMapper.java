package com.chisapp.modules.datareport.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-18 23:03
 * @Version 1.0
 */
public interface MemberReportMapper {

    List<Map<String, Object>> selectMemberAnalysisByCriteria(@Param("sellDays") Integer sellDays,
                                                             @Param("oid") String oid,
                                                             @Param("name") String name,
                                                             @Param("phone") String phone,
                                                             @Param("age") String age,
                                                             @Param("birthSurplusDays") String birthSurplusDays,
                                                             @Param("sysClinicName") String sysClinicName,
                                                             @Param("balance") String balance,
                                                             @Param("czpc") String czpc,
                                                             @Param("czhj") String czhj,
                                                             @Param("xfpc") String xfpc,
                                                             @Param("xfhj") String xfhj);
}
