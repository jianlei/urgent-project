package com.daren.drill.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应急演练管理
 * Created by 张清欣 on 14-7-28.
 */
@Entity
@Table(name = "urg_dri_urgentdrill")
public class UrgentDrillBean extends PersistentEntity {

    private long enterpriseId;      //企业ID
    private long qyid;              //企业id
    private String name;            //演练名称
    private String description;     //描述
    private int is_confirm;         //是否确认：0-未确认；1-确认

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public long getQyid() {
        return qyid;
    }

    public void setQyid(long qyid) {
        this.qyid = qyid;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
