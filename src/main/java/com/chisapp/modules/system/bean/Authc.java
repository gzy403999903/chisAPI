package com.chisapp.modules.system.bean;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Authc implements Serializable {
    private Integer id;

    private Integer moduleOrder;

    @Length(max = 50)
    @NotBlank
    private String moduleName;

    @Length(max = 50)
    @NotBlank
    private String moduleIndex;

    private Integer menuOrder;

    @Length(max = 50)
    @NotBlank
    private String menuName;

    @Length(max = 50)
    @NotBlank
    private String menuIndex;

    private Integer pathOrder;

    @Length(max = 50)
    @NotBlank
    private String pathName;

    @Length(max = 100)
    @NotBlank
    private String pathIndex;

    @Length(max = 1000)
    @NotBlank
    private String roleNames = "0";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getModuleIndex() {
        return moduleIndex;
    }

    public void setModuleIndex(String moduleIndex) {
        this.moduleIndex = moduleIndex == null ? null : moduleIndex.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(String menuIndex) {
        this.menuIndex = menuIndex == null ? null : menuIndex.trim();
    }

    public Integer getPathOrder() {
        return pathOrder;
    }

    public void setPathOrder(Integer pathOrder) {
        this.pathOrder = pathOrder;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName == null ? null : pathName.trim();
    }

    public String getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(String pathIndex) {
        this.pathIndex = pathIndex == null ? null : pathIndex.trim();
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames == null ? null : roleNames.trim();
    }
}
