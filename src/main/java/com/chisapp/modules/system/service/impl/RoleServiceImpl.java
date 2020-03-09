package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.Authc;
import com.chisapp.modules.system.bean.Role;
import com.chisapp.modules.system.dao.RoleMapper;
import com.chisapp.modules.system.service.AuthcService;
import com.chisapp.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/20 13:54
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Role")
@Service
public class RoleServiceImpl implements RoleService {

    private RoleMapper roleMapper;
    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    private AuthcService authcService;
    @Autowired
    public void setAuthcService(AuthcService authcService) {
        this.authcService = authcService;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @CacheEvict(key = "'enabled'")
    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Caching(
            put = {
                    @CachePut(key = "#role.id")
            },
            evict = {
                    @CacheEvict(key = "'enabled'")
            }
    )
    @Override
    public Role update(Role role) {
        roleMapper.updateByPrimaryKey(role);
        return role;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#id"),
                    @CacheEvict(key = "'enabled'")
            }
    )
    @Override
    public void delete(Integer id) {
        roleMapper.deleteByPrimaryKey(id);

        List<Authc> allAuthcList = authcService.getAll();
        authcService.updateRoleNames(allAuthcList, id.toString(), new HashSet<>());
    }

    @Cacheable(key = "#id")
    @Override
    public Role getById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> getByCriteria(String name, Boolean state) {
        return roleMapper.selectByCriteria(name, state);
    }

    @Cacheable(key = "'enabled'")
    @Override
    public List<Role> getEnabled() {
        return roleMapper.selectEnabled();
    }
}
