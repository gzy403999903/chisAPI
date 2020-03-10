package com.chisapp.modules.doctorworkstation.service.impl;

import com.chisapp.modules.doctorworkstation.bean.CommonDiagnose;
import com.chisapp.modules.doctorworkstation.dao.CommonDiagnoseMapper;
import com.chisapp.modules.doctorworkstation.service.CommonDiagnoseService;
import com.chisapp.modules.system.bean.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/6 17:10
 * @Version 1.0
 */
@CacheConfig(cacheNames = "CommonDiagnose")
@Service
public class CommonDiagnoseServiceImpl implements CommonDiagnoseService {

    private CommonDiagnoseMapper commonDiagnoseMapper;
    @Autowired
    public void setCommonDiagnoseMapper(CommonDiagnoseMapper commonDiagnoseMapper) {
        this.commonDiagnoseMapper = commonDiagnoseMapper;
    }

    @Override
    public Integer save(CommonDiagnose commonDiagnose) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取用户信息

        commonDiagnose.setCreatorId(user.getId());
        commonDiagnose.setCreationDate(new Date());
        commonDiagnoseMapper.insert(commonDiagnose);
        return commonDiagnose.getId();
    }

    @CachePut(key = "#commonDiagnose.id")
    @Override
    public CommonDiagnose update(CommonDiagnose commonDiagnose) {
        commonDiagnoseMapper.updateByPrimaryKey(commonDiagnose);
        return commonDiagnose;
    }

    @CacheEvict(key = "#id")
    @Override
    public void delete(Integer id) {
        commonDiagnoseMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(key = "#id")
    @Override
    public CommonDiagnose getById(Integer id) {
        return commonDiagnoseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(Integer creatorId,
                                                   Integer dwtDiagnoseTypeId,
                                                   Boolean shareState,
                                                   String name) {
        return commonDiagnoseMapper.selectByCriteria(creatorId, dwtDiagnoseTypeId, shareState, name);
    }

    @Override
    public List<CommonDiagnose> getLikeByName(Integer dwtDiagnoseTypeId, String name) {
        return commonDiagnoseMapper.selectLikeByName(dwtDiagnoseTypeId, name);
    }


}
