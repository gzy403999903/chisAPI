package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.ClinicSellTarget;
import com.chisapp.modules.system.dao.ClinicSellTargetMapper;
import com.chisapp.modules.system.service.ClinicSellTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-04-16 15:00
 * @Version 1.0
 */
@Service
public class ClinicSellTargetServiceImpl implements ClinicSellTargetService {

    private ClinicSellTargetMapper clinicSellTargetMapper;
    @Autowired
    public void setClinicSellTargetMapper(ClinicSellTargetMapper clinicSellTargetMapper) {
        this.clinicSellTargetMapper = clinicSellTargetMapper;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveOrUpdate(ClinicSellTarget clinicSellTarget) {
        ClinicSellTarget record = this.getById(clinicSellTarget.getId());
        if (record == null) {
            clinicSellTargetMapper.insert(clinicSellTarget);
        } else {
            clinicSellTargetMapper.updateByPrimaryKey(clinicSellTarget);
        }

    }

    @Override
    public void delete(Integer id) {
        clinicSellTargetMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ClinicSellTarget getById(Integer id) {
        return clinicSellTargetMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String sysClinicName) {
        return clinicSellTargetMapper.selectByCriteria(sysClinicName);
    }



}
