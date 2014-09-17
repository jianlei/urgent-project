package com.daren.production.entities;

import com.daren.core.api.persistence.WorkflowEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;

/**
 * @类描述：安全生产许可证(非煤矿矿山企业)
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "gov_safety_production")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductionBean extends WorkflowEntity {

    private String name;//单位名称
    private String head;//主要负责人
    private String phone;//负责人电话
    private String address;//单位地址
    private String economicsType;//经济类型
    private String scope;//许可范围

    private String code;//证书编号
    private String card;//FM安许证字
    private String unitsDate;//发证机关
    private Date awardDate;//发证日期
    private Date effectiveDate;//有效日期

    private String linkHandle;//当前办理环节
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEconomicsType() {
        return economicsType;
    }

    public void setEconomicsType(String economicsType) {
        this.economicsType = economicsType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnitsDate() {
        return unitsDate;
    }

    public void setUnitsDate(String unitsDate) {
        this.unitsDate = unitsDate;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLinkHandle() {
        return linkHandle;
    }

    public void setLinkHandle(String linkHandle) {
        this.linkHandle = linkHandle;
    }
}
