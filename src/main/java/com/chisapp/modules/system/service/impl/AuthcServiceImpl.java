package com.chisapp.modules.system.service.impl;

import com.chisapp.common.authority.component.ShiroFilterChainDefinitionMapUpdater;
import com.chisapp.common.component.TreeNode;
import com.chisapp.modules.system.bean.Authc;
import com.chisapp.modules.system.dao.AuthcMapper;
import com.chisapp.modules.system.service.AuthcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Tandy
 * @Date: 2019/7/21 18:36
 * @Version 1.0
 */
@CacheConfig(cacheNames = "Authc")
@Service
public class AuthcServiceImpl implements AuthcService {

    private AuthcMapper authcMapper;
    @Autowired
    public void setAuthcMapper(AuthcMapper authcMapper) {
        this.authcMapper = authcMapper;
    }

    private ShiroFilterChainDefinitionMapUpdater filterChainDefinitionMapUpdater;
    @Lazy
    @Autowired
    public void setFilterChainDefinitionMapUpdater(ShiroFilterChainDefinitionMapUpdater filterChainDefinitionMapUpdater) {
        this.filterChainDefinitionMapUpdater = filterChainDefinitionMapUpdater;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    @CacheEvict(key = "'all'", beforeInvocation = true)
    @Override
    public void updateRoleNames(List<Authc> allAuthcList, String roleName, HashSet<Integer> checkedAuthcSet) {
        // 提交的 Map
        Map<Integer, String> changedAuthcMap = new HashMap<>();

        // 遍历 allAuthcList 判断其 roleNames 是否发生改变
        for (Authc authc : allAuthcList) {
            Integer key = authc.getId();
            String roleNames = authc.getRoleNames();

            // 如果 allAuthcList 当前记录 id 为选中状态, 则进行添加操作
            if (checkedAuthcSet.contains(key)) {
                String tempRoleNames = this.addRoleNameToRoleNames(roleName, roleNames);
                if (!roleNames.equals(tempRoleNames)) {
                    changedAuthcMap.put(key, tempRoleNames);
                }
            }

            // 如果 allAuthcList 当前记录 id 为未中状态, 则进行删除操作
            if (!checkedAuthcSet.contains(key)) {
                String tempRoleNames = this.removeRoleNameInRoleNames(roleName, roleNames);
                if (!roleNames.equals(tempRoleNames)) {
                    changedAuthcMap.put(key, tempRoleNames);
                }
            }
        }

        if (changedAuthcMap.size() > 0) {
            authcMapper.updateRoleNamesByMap(changedAuthcMap);
            filterChainDefinitionMapUpdater.update();
        }
    }

    @CacheEvict(key = "'all'", beforeInvocation = true)
    @Override
    public void saveRoleNames(List<Authc> allAuthcList, String roleName, HashSet<Integer> checkedAuthcSet) {
        // 提交的 Map
        Map<Integer, String> changedAuthcMap = new HashMap<>();

        // 遍历 allAuthcList 判断其 roleNames 是否发生改变
        for (Authc authc : allAuthcList) {
            int key = authc.getId();
            String roleNames = authc.getRoleNames();

            // 如果 allAuthcList 当前记录 id 为选中状态, 则进行添加操作
            if (checkedAuthcSet.contains(key)) {
                String tempRoleNames = this.addRoleNameToRoleNames(roleName, roleNames);
                if (!roleNames.equals(tempRoleNames)) {
                    changedAuthcMap.put(key, tempRoleNames);
                }
            }
        }

        if (changedAuthcMap.size() > 0) {
            authcMapper.updateRoleNamesByMap(changedAuthcMap);
            filterChainDefinitionMapUpdater.update();
        }
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Authc> getAll() {
        return authcMapper.selectAll();
    }

    @Override
    public List<TreeNode> getTree(List<Authc> allAuthcList) {
        // 获取一级节点
        List<TreeNode> tree = this.getLeve1(allAuthcList);

        // 获取二级节点(一级节点的 children)
        for (TreeNode leve1 : tree) {
            List<TreeNode> leve2 = this.getLeve2(allAuthcList, leve1.getLabel());
            leve1.setChildren(leve2);
        }

        // 获取三级节点(二级节点的 children)
        for (TreeNode leve1 : tree) {
            for (TreeNode leve2 : leve1.getChildren()) {
                List<TreeNode> leve3 = this.getLeve3(allAuthcList, leve1.getLabel(), leve2.getLabel());
                leve2.setChildren(leve3);
            }
        }
        return tree;
    }

    /**
     * 获取一级节点
     * @param authcList
     * @return
     */
    private List<TreeNode> getLeve1 (List<Authc> authcList) {
        List<TreeNode> leve1 = new LinkedList<>();
        TreeNode treeNode = null;
        String moduleName = "";
        int i = 1;
        for (Authc authc : authcList) {
            if (!moduleName.equals(authc.getModuleName())) {
                moduleName = authc.getModuleName();

                treeNode = new TreeNode();
                treeNode.setId((1000 + i++) * -1);
                treeNode.setLabel(moduleName);

                leve1.add(treeNode);
            }
        }
        return leve1;
    }

    /**
     * 获取二级节点
     * @param authcList
     * @param moduleName
     * @return
     */
    private List<TreeNode> getLeve2 (List<Authc> authcList, String moduleName) {
        List<TreeNode> leve2 = new LinkedList<>();
        TreeNode treeNode = null;
        String menuName = "";
        int i = 1;
        for (Authc authc : authcList) {
            if (moduleName.equals(authc.getModuleName())) {
                if (!menuName.equals(authc.getMenuName())) {
                    menuName = authc.getMenuName();

                    treeNode = new TreeNode();
                    treeNode.setId((2000 + i++) * -1);
                    treeNode.setLabel(menuName);

                    leve2.add(treeNode);
                }
            }
        }
        return leve2;
    }

    /**
     * 获取三级节点
     * @param authcList
     * @param menuName
     * @return
     */
    private List<TreeNode> getLeve3 (List<Authc> authcList, String moduleName, String menuName) {
        List<TreeNode> leve3 = new LinkedList<>();
        TreeNode treeNode = null;
        for (Authc authc : authcList) {
            if (moduleName.equals(authc.getModuleName()) && menuName.equals(authc.getMenuName())) {
                treeNode = new TreeNode();
                treeNode.setId(authc.getId());
                treeNode.setLabel(authc.getPathName());
                treeNode.setRoleNames(authc.getRoleNames());
                leve3.add(treeNode);
            }
        }
        return leve3;
    }

}
