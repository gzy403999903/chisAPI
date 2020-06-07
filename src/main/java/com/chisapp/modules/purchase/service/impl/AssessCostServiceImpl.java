package com.chisapp.modules.purchase.service.impl;

import com.chisapp.modules.purchase.bean.AssessCost;
import com.chisapp.modules.purchase.dao.AssessCostMapper;
import com.chisapp.modules.purchase.service.AssessCostService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020-06-04 11:24
 * @Version 1.0
 */
@Service
public class AssessCostServiceImpl implements AssessCostService {

    @Autowired
    private AssessCostMapper assessCostMapper;
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void save(AssessCost assessCost) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取操作人信息
        assessCost.setCreatorId(user.getId());
        assessCost.setCreationDate(new Date());

        assessCostMapper.insert(assessCost);
    }

    @Override
    public void update(AssessCost assessCost) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取操作人信息
        assessCost.setLastUpdaterId(user.getId());
        assessCost.setLastUpdateDate(new Date());

        assessCostMapper.updateByPrimaryKey(assessCost);
    }

    @Override
    public void delete(Integer id) {
        assessCostMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AssessCost getById(Integer id) {
        return assessCostMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AssessCost> getBySupplierIdAndGoodsIdList(Integer pemSupplierId,
                                                          List<Integer> gsmGoodsIdList) {
        return assessCostMapper.selectBySupplierIdAndGoodsIdList(pemSupplierId, gsmGoodsIdList);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String pemSupplierOid,
                                                   String pemSupplierName,
                                                   String gsmGoodsOid,
                                                   String gsmGoodsName) {

        return assessCostMapper.selectByCriteria(pemSupplierOid, pemSupplierName, gsmGoodsOid, gsmGoodsName);
    }
}
