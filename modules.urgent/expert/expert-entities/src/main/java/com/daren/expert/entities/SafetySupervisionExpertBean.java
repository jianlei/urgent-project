package com.daren.expert.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 行管专家
 * Created by 张清欣 on 14-7-28.
 */
@Entity
@Table(name = "urg_exp_safety_supervision")
public class SafetySupervisionExpertBean extends PersistentEntity {
    private long attach;                  //专家归属（专家是哪个企业的）
    private String contactInformation;  //联系方式
    private long declareType;           //申报状态
    private String name;                 //专家名称
    private String type;                //分类

    public long getAttach() {
        return attach;
    }

    public void setAttach(long attach) {
        this.attach = attach;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public long getDeclareType() {
        return declareType;
    }

    public void setDeclareType(long declareType) {
        this.declareType = declareType;
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
}
