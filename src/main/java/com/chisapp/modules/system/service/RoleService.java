package com.chisapp.modules.system.service;

import com.chisapp.modules.system.bean.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/20 13:33
 * @Version 1.0
 */
public interface RoleService {
    /**
     * 保存角色 和 对应的权限
     * 必须返回保存 Role 时数据库主键 id
     * 在对应的 mapper.xml 上添加属性 useGeneratedKeys="true" keyProperty="id" keyColumn="id"
     * 或在 mapeper 上使用注解 @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
     * 返回的主键 id 只能通过对象的 get方法获取, 例如 role.getId()
     * @param role
     */
    @Transactional
    void save(Role role);

    /**
     * 修改操作
     * @param role
     * @return
     */
    @Transactional
    Role update(Role role);

    /**
     * 删除操作
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    Role getById(Integer id);

    /**
     * 根据条件获取对象集合
     * @param name
     * @param state
     * @return
     */
    List<Role> getByCriteria(String name, Boolean state);

    /**
     * 获取所有对象集合
     * @return
     */
    List<Role> getEnabled();
}
