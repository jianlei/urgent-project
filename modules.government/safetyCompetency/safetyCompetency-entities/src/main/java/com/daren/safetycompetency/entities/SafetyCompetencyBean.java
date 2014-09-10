package com.daren.safetycompetency.entities;

import com.daren.core.api.persistence.WorkflowEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;

/**
 * @类描述：安全资格证书(培训)
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "gov_safety_competency")
@Inheritance(strategy = InheritanceType.JOINED)
public class SafetyCompetencyBean extends WorkflowEntity {
    private Date awardDate;//发证日期
    private Date effectiveDate;//有效日期
    private String code;//证书编号
    private String name;//姓名
    private String sex;//性别
    private String title;//职称
    private String cultureLevel;//文化程度
    private String id_code;//身份证号
    private String unitType;//单位类型
    private String qualificationsType;//资格类型
    private long proposerId;//审请人ID
    private long enterpriseId;//企业ID

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCultureLevel() {
        return cultureLevel;
    }

    public void setCultureLevel(String cultureLevel) {
        this.cultureLevel = cultureLevel;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getQualificationsType() {
        return qualificationsType;
    }

    public void setQualificationsType(String qualificationsType) {
        this.qualificationsType = qualificationsType;
    }

    public long getProposerId() {
        return proposerId;
    }

    public void setProposerId(long proposerId) {
        this.proposerId = proposerId;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
