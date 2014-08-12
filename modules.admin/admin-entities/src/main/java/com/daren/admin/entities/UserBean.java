package com.daren.admin.entities;

import com.daren.core.api.persistence.PersistentEntity;
import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;


/**
 * @类描述：用户实体实现类
 * @创建人：sunlf
 * @创建时间：2014-04-26 上午11:10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement(name = "UserBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserBean extends PersistentEntity {
    @XmlTransient
    private OfficeBean company;    // 归属公司
    @XmlTransient
    private OfficeBean office;    // 归属部门
    @NotNull(message = "'登录名'是必填项")
//    @XmlAttribute
    private String loginName;// 登录名
    //    @XmlAttribute
    @NotNull(message = "'姓名'是必填项")
    private String name;  // 姓名
    //    @XmlAttribute
    @NotNull(message = "'密码'是必填项")
    private String password; // 密码
    @NotNull(message = "'邮箱'是必填项")
    private String email; // 邮箱
    private String phone;    // 电话
    @NotNull(message = "'手机'是必填项")
    private String mobile;    // 手机
    private String loginIp;    // 最后登陆IP
    private Date loginDate;    // 最后登陆日期
    //    @XmlElement(name="role")
    @XmlTransient
    private List<RoleBean> roleList = Lists.newArrayList(); // 拥有角色列表

    public UserBean() {

    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getLoginName() {
        return loginName;
    }


    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getLoginIp() {
        return loginIp;
    }


    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }


    public Date getLoginDate() {
        return loginDate;
    }


    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {
                    "user_id", "role_id"})})
    @OrderBy("id")
    public List<RoleBean> getRoleList() {
        return roleList;
    }


    public void setRoleList(List<RoleBean> roleList) {
        this.roleList = roleList;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public OfficeBean getCompany() {
        return company;
    }

    public void setCompany(OfficeBean company) {
        this.company = company;
    }

    @ManyToOne
    @JoinColumn(name = "office_id")
    public OfficeBean getOffice() {
        return office;
    }

    public void setOffice(OfficeBean office) {
        this.office = office;
    }
}
