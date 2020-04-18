package com.chisapp.modules.datareport.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-18 22:40
 * @Version 1.0
 */
public interface MemberReportService {
    /**
     * 会员分析
     * @param sellDays 查询的销售周期天数
     * @param oid 编码
     * @param name 姓名
     * @param phone 电话
     * @param age 年龄 (注入条件运算符)
     * @param birthSurplusDays 距离生日天数 (注入条件运算符)
     * @param sysClinicName 注册机构名称
     * @param balance 剩余储值金额 (注入条件运算符)
     * @param czpc 储值频次 (注入条件运算符)
     * @param czhj 储值合计 (注入条件运算符)
     * @param xfpc 消费频次 (注入条件运算符)
     * @param xfhj 消费合计 (注入条件运算符)
     * @return
     */
    List<Map<String, Object>> getMemberAnalysisByCriteria(Integer sellDays,
                                                          String oid,
                                                          String name,
                                                          String phone,
                                                          String age,
                                                          String birthSurplusDays,
                                                          String sysClinicName,
                                                          String balance,
                                                          String czpc,
                                                          String czhj,
                                                          String xfpc,
                                                          String xfhj);

}
