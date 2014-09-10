package com.daren.fireworks.entities;

import com.daren.core.impl.persistence.WorkflowEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;

/**
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "gov_fireworks")
@Inheritance(strategy = InheritanceType.JOINED)
public class FireworksBean extends WorkflowEntity {
    private String code;//登记编号
    private String card;//FM安许证字
    private String name;//单位名称
    private String head;//主要负责人
    private String address;//注册地址
    private String economicsType;//经济类型
    private String storageAddress;//仓储设施地址
    private String scope;//许可经营范围
    private Date validityDate;//有效期
    private Date unitsDate;//发证机关
    private long proposerId;//审请人ID
    private long enterpriseId;//企业ID

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

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public Date getUnitsDate() {
        return unitsDate;
    }

    public void setUnitsDate(Date unitsDate) {
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
}
