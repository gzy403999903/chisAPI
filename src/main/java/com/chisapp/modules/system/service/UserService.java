package com.chisapp.modules.system.service;

import com.chisapp.common.utils.EncryptionUtils;
import com.chisapp.modules.system.bean.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/7/20 16:38
 * @Version 1.0
 */
public interface UserService {
    /**
     * 保存操作
     * @param user
     * @return
     */
    @Transactional
    void save(User user);

    /**
     * 修改操作
     * @param user
     * @return
     */
    @Transactional
    User update(User user);

    /**
     * 删除操作
     * @param user
     * @return
     */
    @Transactional
    void delete(User user);

    /**
     * 初始化密码
     * @param user
     * @return
     */
    @Transactional
    User initPassword(User user);

    /**
     * 修改密码
     * @param user
     * @param oldPass
     * @param pass
     * @return
     */
    @Transactional
    User updatePassword(User user, String oldPass, String pass);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 根据 account 获取对象
     * account 必须唯一, 可以通过设置数据库 UNIQUE 索引
     * 或者在保存或修改时检查该记录是否存在,从而保证其唯一性
     * @param account
     * @return
     */
    User getByAccount(String account);

    /**
     * 根据指定条件获取对象集合 (视图查询)
     * @param name
     * @param sysRoleName
     * @param sysClinicName
     * @param state
     * @return
     */
    List<Map<String, Object>> getByCriteria(String name, String sysRoleName,
                                            String sysClinicName, Boolean state);

    /**
     * 获取对应机构启用状态的用户
     * @param sysClinicId
     * @return
     */
    List<Map<String, Object>> getClinicEnabled(Integer sysClinicId);

    /**
     * 对密码进行加密
     * @param password
     * @param account
     * @return
     */
    default String encryption(String password, String account) {
        return EncryptionUtils.getShiroSaltCode(password, account);
    }
}
