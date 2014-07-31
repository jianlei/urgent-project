package com.daren.regulation.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 政务法规
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "urg_reg_regulation")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class RegulationBean extends PersistentEntity {
    private Long enterpriseId;      //企业ID
    private String name;             //名称
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
