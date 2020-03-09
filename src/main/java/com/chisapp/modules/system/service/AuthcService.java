package com.chisapp.modules.system.service;

import com.chisapp.common.component.TreeNode;
import com.chisapp.modules.system.bean.Authc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: Tandy
 * @Date: 2019/7/21 18:29
 * @Version 1.0
 */
public interface AuthcService {

    /**
     *  Authc.roleNames 中默认的值
     */
    String  DEFAULT_ROLENAMES_VALUE = "0";

    /**
     * 角色为编辑时 修改权限中 roleNames 允许访问的角色
     * 需在执行方法前先清除缓存 因为方法结束时会查询所有权限并进行更新 如果不删除获取到的则是没有改变的权限
     * @param checkedAuthcSet 该角色对应的权限记录 id 的集合</>
     */
    @Transactional
    void updateRoleNames(List<Authc> allAuthcList, String roleName, HashSet<Integer> checkedAuthcSet);

    /**
     * 当角色为新建时 修改权限中 roleNames 允许访问的角色
     * 需在执行方法前先清除缓存 因为方法结束时会查询所有权限并进行更新 如果不删除获取到的则是没有改变的权限
     * @param roleName
     * @param checkedAuthcSet 该角色对应的权限记录 id 的集合</>
     */
    @Transactional
    void saveRoleNames(List<Authc> allAuthcList, String roleName, HashSet<Integer> checkedAuthcSet);

    /**
     * 获取所有对象集合
     * @return
     */
    List<Authc> getAll();

    /**
     * 获取 Tree 数据模型
     * @return
     */
    List<TreeNode> getTree(List<Authc> allAuthcList);

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * 将 roleName 添加至 roleNames
     * @param roleName
     * @param roleNames
     * @return
     */
    default String addRoleNameToRoleNames(String roleName, String roleNames) {
        if (!this.isContain(roleName, roleNames)) {
            return roleNames.trim().equals("") ? (DEFAULT_ROLENAMES_VALUE + "," +  roleName.trim()) : (roleNames.trim() + "," + roleName.trim());
        } else {
            return roleNames;
        }
    }

    /**
     * 将 roleName 从 roleNames 中删除
     * @param roleName
     * @param roleNames
     * @return
     */
    default String removeRoleNameInRoleNames(String roleName, String roleNames) {
        String tempRoleNames = DEFAULT_ROLENAMES_VALUE;
        if (this.isContain(roleName, roleNames)) {
            String roleNameArray[] = roleNames.split(",");

            for (int i = 0; i < roleNameArray.length; i++) {
                String tempRoleName = roleNameArray[i];

                if (!roleName.equals(tempRoleName)) {
                    tempRoleNames = this.addRoleNameToRoleNames(tempRoleName, tempRoleNames);
                }
            }
        } else {
            tempRoleNames = roleNames;
        }
        return tempRoleNames;
    }

    /**
     * 判断 roleNames 中是否已经包含 roleName
     * @param roleName
     * @param roleNames
     * @return
     */
    default Boolean isContain(String roleName, String roleNames) {
        if (roleName == null) {
            throw new RuntimeException("roleName 不能为 null 值");
        }

        if (roleNames == null) {
            throw new RuntimeException("roleNames 不能为 null 值");
        }

        String roleNameArray[] = roleNames.split(",");
        for (String tempRoleName : roleNameArray) {
            if (roleName.equals(tempRoleName)) {
                return true;
            }
        }

        return false;
    }
}
