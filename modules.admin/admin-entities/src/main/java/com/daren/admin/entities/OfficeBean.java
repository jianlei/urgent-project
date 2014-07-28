package com.daren.admin.entities;

import com.daren.core.api.persistence.PersistentEntity;
import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @类描述：机构实体类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午3:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_office")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class OfficeBean extends PersistentEntity {

    private OfficeBean parent;    // 父级编号
    private String parentIds; // 所有父级编号
    private AreaBean areaBean;        // 归属区域
    private String code;    // 机构编码
    private String name;    // 机构名称
    private String type;    // 机构类型（1：公司；2：部门；3：小组）
    private String grade;    // 机构等级（1：一级；2：二级；3：三级；4：四级）
    private String address; // 联系地址
    private String zipCode; // 邮政编码
    private String master;    // 负责人
    private String phone;    // 电话
    private String fax;    // 传真
    private String email;    // 邮箱

    private List<UserBean> userList = Lists.newArrayList();   // 拥有用户列表
    private List<OfficeBean> childList = Lists.newArrayList();// 拥有子机构列表

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public OfficeBean getParent() {
        return parent;
    }

    public void setParent(OfficeBean parent) {
        this.parent = parent;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY)
    @OrderBy(value = "id")
    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy(value = "code")
    public List<OfficeBean> getChildList() {
        return childList;
    }

    public void setChildList(List<OfficeBean> childList) {
        this.childList = childList;
    }

    @ManyToOne
    @JoinColumn(name = "area_id")
    @NotNull
    public AreaBean getAreaBean() {
        return areaBean;
    }

    public void setAreaBean(AreaBean areaBean) {
        this.areaBean = areaBean;
    }
}
