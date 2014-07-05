package com.daren.admin.entities;

import com.daren.core.impl.persistence.PersistentEntity;
import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @类描述：角色实现类
 * @创建人：sunlf
 * @创建时间：2014-04-26 上午11:07
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_role")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class RoleBeanImpl extends PersistentEntity {
    private String name;    // 角色名称
    private String remark;  //角色备注

    private List<UserBeanImpl> userList = Lists.newArrayList(); // 拥有用户列表
    private List<PermissionBeanImpl> permissionList = Lists.newArrayList(); // 拥有菜单列表

    public RoleBeanImpl() {
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    @ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("id")
    public List<UserBeanImpl> getUserList() {
        return userList;
    }


    public void setUserList(List<UserBeanImpl> userList) {
        this.userList = userList;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @OrderBy("id")
    public List<PermissionBeanImpl> getPermissionList() {
        return permissionList;
    }


    public void setPermissionList(List<PermissionBeanImpl> permissionList) {
        this.permissionList = permissionList;
    }
}
