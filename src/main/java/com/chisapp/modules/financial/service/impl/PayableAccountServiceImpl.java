package com.chisapp.modules.financial.service.impl;

import com.chisapp.modules.financial.bean.PayableAccount;
import com.chisapp.modules.financial.dao.PayableAccountMapper;
import com.chisapp.modules.financial.service.PayableAccountService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2020/2/16 19:56
 * @Version 1.0
 */
@Service
public class PayableAccountServiceImpl implements PayableAccountService {

    private PayableAccountMapper payableAccountMapper;
    @Autowired
    public void setPayableAccountMapper(PayableAccountMapper payableAccountMapper) {
        this.payableAccountMapper = payableAccountMapper;
    }

    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public void saveList(List<PayableAccount> payableAccountList) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        PayableAccountMapper mapper = batchSqlSession.getMapper(PayableAccountMapper.class);
        try {
            for (PayableAccount payableAccount : payableAccountList) {
                mapper.insert(payableAccount);
            }
            batchSqlSession.commit();
        } finally {
            batchSqlSession.close();
        }
    }

    @Override
    public List<Map<String, Object>> getGroupListByCriteria(String[] creationDate, Integer pemSupplierId, Integer sysClinicId, BigDecimal arrearagesAmount) {
        return payableAccountMapper.selectGroupListByCriteria(creationDate, pemSupplierId, sysClinicId, arrearagesAmount);
    }

    @Override
    public List<Map<String, Object>> getByLsh(String lsh) {
        return payableAccountMapper.selectByLsh(lsh);
    }

}
