package com.chisapp.modules.datareport.service.impl;

import com.chisapp.modules.datareport.dao.MemberReportMapper;
import com.chisapp.modules.datareport.service.MemberReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-18 23:01
 * @Version 1.0
 */
@Service
public class MemberReportServiceImpl implements MemberReportService {

    private MemberReportMapper memberReportMapper;
    @Autowired
    public void setMemberReportMapper(MemberReportMapper memberReportMapper) {
        this.memberReportMapper = memberReportMapper;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public List<Map<String, Object>> getMemberAnalysisByCriteria(Integer sellDays,
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
                                                                 String xfhj) {

        return memberReportMapper.selectMemberAnalysisByCriteria(sellDays, oid, name, phone, age, birthSurplusDays,
                sysClinicName, balance, czpc, czhj, xfpc, xfhj);
    }



}
