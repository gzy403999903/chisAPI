package com.chisapp.modules.system.service.impl;

import com.chisapp.modules.system.bean.User;
import com.chisapp.modules.system.dao.UserMapper;
import com.chisapp.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: Tandy
 * @Date: 2019/7/20 18:54
 * @Version 1.0
 */
@CacheConfig(cacheNames = "User")
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @CacheEvict(key = "'clinicEnabled' + #user.sysClinicId")
    @Override
    public void save(User user) {
        String password = this.encryption(new User().getPassword(), user.getAccount());
        user.setPassword(password);
        userMapper.insert(user);
    }

    @Caching(
            put = {
                    @CachePut(key = "#user.id")
            },
            evict = {
                    @CacheEvict(key = "'clinicEnabled' + #user.sysClinicId")
            }
    )
    @Override
    public User update(User user) {
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#user.id"),
                    @CacheEvict(key = "'clinicEnabled' + #user.sysClinicId")
            }
    )
    @Override
    public void delete(User user) {
        userMapper.deleteByPrimaryKey(user.getId());
    }

    @CachePut(key = "#user.id")
    @Override
    public User initPassword(User user) {
        String password = this.encryption(new User().getPassword(), user.getAccount());
        user.setPassword(password);
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @CachePut(key = "#user.id")
    @Override
    public User updatePassword(User user, String oldPass, String pass) {
        if (pass.equals(new User().getPassword())) {
            throw new RuntimeException("不能使用初始密码");
        }

        oldPass = this.encryption(oldPass, user.getAccount());
        if (!oldPass.equals(user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }

        // 必须包含大写字母、数字、特殊字符、至少6个字符、最多30个字符
        // String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{6,30}";
        // 必须包含字母、数字、特殊字符、至少6个字符、最多30个字符
        // String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,30}";
        // 必须包含字母、数字、至少6个字符、最多30个字符
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z]).{6,30}";

        boolean isMatch = Pattern.matches(pattern, pass);
        if (!isMatch) {
            throw new RuntimeException("必须包含字母、数字、至少6个字符、最多30个字符");
        }

        pass = this.encryption(pass, user.getAccount());
        user.setPassword(pass);
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Cacheable(key = "#id")
    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    @Override
    public List<Map<String, Object>> getByCriteria(String name, String sysRoleName, String sysClinicName, Boolean state) {
        return userMapper.selectByCriteria(name, sysRoleName, sysClinicName, state);
    }

    @Cacheable(key = "'clinicEnabled' + #sysClinicId")
    @Override
    public List<Map<String, Object>> getClinicEnabled(Integer sysClinicId) {
        return userMapper.selectClinicEnabled(sysClinicId);
    }
}
