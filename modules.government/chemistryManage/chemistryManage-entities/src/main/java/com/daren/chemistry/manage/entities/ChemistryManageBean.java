package com.daren.chemistry.manage.entities;

import com.daren.core.api.persistence.WorkflowEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @类描述：危险化学品经营许可证
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "gov_ChemistryManage")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ChemistryManageBean extends WorkflowEntity {

    private String name;//经营单位名称
    private String header;//经营单位负责人
    private String phone;//负责人电话
    private String address;//经营单位住所
    private String  mode;//经营方式
    private String unitType;//经营单位类型
    private String scope;//许可经营范围

    private String code;//登记编号
    private Date startDate;//有效日期开始
    private Date endDate;//有效日期结束
    private String unitsDate;//发证机关

    private String linkHandle;//当前办理环节
    private String proposerId;//审请人ID
    private String qyid;//企业ID

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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUnitsDate() {
        return unitsDate;
    }

    public void setUnitsDate(String unitsDate) {
        this.unitsDate = unitsDate;
    }

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }
}
