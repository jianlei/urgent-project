package com.daren.admin.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 项目名称:  urgent-project
 * 类描述:    存储系统版本信息实体
 * 创建人:    sunlf
 * 创建时间:  2014/8/11 8:51
 * 修改人:    sunlf
 * 修改时间:  2014/8/11 8:51
 * 修改备注:  [说明本次修改内容]
 */
@Entity
@Table(name = "sys_about")
@Inheritance(strategy = InheritanceType.JOINED)
public class AboutBean extends PersistentEntity {
    private static final long serialVersionUID = 1L;

    private String systemName;//系统名称
    private String systemVersion;//系统版本号
    private String userName;//用户名称
    private String code;//系统编码()

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
