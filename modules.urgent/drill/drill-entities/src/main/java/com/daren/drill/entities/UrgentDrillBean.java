package com.daren.drill.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 应急演练管理
 * Created by 张清欣 on 14-7-28.
 */
@Entity
@Table(name = "urg_dri_urgentdrill")
public class UrgentDrillBean extends PersistentEntity {
    private Long enterpriseId;      //企业ID
    private String name;             //演练名称
    private String description;     //描述

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
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
