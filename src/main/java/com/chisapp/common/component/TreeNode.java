package com.chisapp.common.component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 这是一个符合前端 Tree 数据结构的模型 该模型是 Tree 的一个节点
 * 由多个节点组成一个 Tree 例如 List<Tree></>
 * @Author: Tandy
 * @Date: 2019/7/22 8:47
 * @Version 1.0
 */
public class TreeNode implements Serializable {
    // id 不能为 null 也不能重复, 否则前端组件会报错
    @NotNull
    private Integer id;
    private String label;
    private String roleNames;
    private List<TreeNode> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", roleNames='" + roleNames + '\'' +
                ", children=" + children +
                '}';
    }
}
