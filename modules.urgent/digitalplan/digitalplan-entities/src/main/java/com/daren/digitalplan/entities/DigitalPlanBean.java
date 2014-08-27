package com.daren.digitalplan.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "urg_digitalplan")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DigitalPlanBean extends PersistentEntity {
    private String name;//预案名称
    private String description;//描述
    private String mark;//标识
    private String level;//级别
    private String type;//类型
    private String approveType;//审批状态
    private Date approveTime;//审批时间
    private String digitalPlanId;//安监局预案文档ID

    public DigitalPlanBean() {
        name = "";
        description = "";
        mark = "";
        level = "";
        type = "";
        approveType = "审批通过";
        approveTime = new Date();
        digitalPlanId = "";
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getDigitalPlanId() {
        return digitalPlanId;
    }

    public void setDigitalPlanId(String digitalPlanId) {
        this.digitalPlanId = digitalPlanId;
    }
}
