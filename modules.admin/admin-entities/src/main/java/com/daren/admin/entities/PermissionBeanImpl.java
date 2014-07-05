package com.daren.admin.entities;

import com.daren.core.impl.persistence.PersistentEntity;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

/**
 * @类描述：权限实体实现类
 * @创建人：sunlf
 * @创建时间：2014-04-26 上午11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_permission")
@Inheritance(strategy = InheritanceType.JOINED)
public class PermissionBeanImpl extends PersistentEntity {
    private PermissionBeanImpl parent;    // 父级菜单
    private String parentIds; // 所有父级编号
    private String name;    // 名称
    private String href;    // 链接
    private String icon;    // 图标
    private Integer sort;    // 排序
    private String isShow;    // 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识

    private List<PermissionBeanImpl> childList = Lists.newArrayList();// 拥有子菜单列表
    private List<RoleBeanImpl> roleList = Lists.newArrayList(); // 拥有角色列表


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public PermissionBeanImpl getParent() {
        return (PermissionBeanImpl) parent;
    }


    public void setParent(PermissionBeanImpl parent) {
        this.parent = parent;
    }


    public String getParentIds() {
        return parentIds;
    }


    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getHref() {
        return href;
    }


    public void setHref(String href) {
        this.href = href;
    }


    public String getIcon() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Integer getSort() {
        return sort;
    }


    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public String getIsShow() {
        return isShow;
    }


    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }


    public String getPermission() {
        return permission;
    }


    public void setPermission(String permission) {
        this.permission = permission;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy(value = "sort")
    public List<PermissionBeanImpl> getChildList() {
        return childList;
    }


    public void setChildList(List<PermissionBeanImpl> childList) {
        this.childList = childList;
    }


    @ManyToMany(mappedBy = "permissionList", fetch = FetchType.EAGER)
    @OrderBy("id")
    public List<RoleBeanImpl> getRoleList() {
        return roleList;
    }


    public void setRoleList(List<RoleBeanImpl> roleList) {
        this.roleList = roleList;
    }
}
