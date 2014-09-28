package com.daren.fireworks.entities;

import com.daren.core.api.persistence.WorkflowEntity;

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
    private String name;//单位名称
    private String head;//主要负责人
    private String address;//注册地址
    private String postalCode;//邮政编码
    private String storageAddress;//仓储设施地址
    private String economicsType;//经济类型
    private String registrationCapital;//注册资本
    private String phone;//联系电话
    private String fax;//传真
    private String website;//单位网址
    private String mail;//电子信箱
    private String businessCode;//工商注册号
    private Date registrationDate;//登记日期
    private String registrationUnits;//登记机关
    private String fixedAssets;//固定资产总值
    private String sales;//销售额
    private String exitusSales;//出口额
    private String workersNumber;//从业人员
    private String safety;//安全管理人员
    private String warehouse;//仓库保管和守护人员
    private String transport;//运输车辆
    private String scope;//许可经营范围
    private String products;//产品分级

    private String code;//登记编号
    private String card;//FM安许证字
    private Date validityDate;//有效期
    private Date unitsDate;//发证机关

    private String linkHandle;//当前办理环节
    private String loginName;//申请人登陆名
    private long proposerId;//审请人ID
    private long enterpriseId;//企业ID

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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getEconomicsType() {
        return economicsType;
    }

    public void setEconomicsType(String economicsType) {
        this.economicsType = economicsType;
    }

    public String getRegistrationCapital() {
        return registrationCapital;
    }

    public void setRegistrationCapital(String registrationCapital) {
        this.registrationCapital = registrationCapital;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationUnits() {
        return registrationUnits;
    }

    public void setRegistrationUnits(String registrationUnits) {
        this.registrationUnits = registrationUnits;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getExitusSales() {
        return exitusSales;
    }

    public void setExitusSales(String exitusSales) {
        this.exitusSales = exitusSales;
    }

    public String getWorkersNumber() {
        return workersNumber;
    }

    public void setWorkersNumber(String workersNumber) {
        this.workersNumber = workersNumber;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
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

    public String getLinkHandle() {
        return linkHandle;
    }

    public void setLinkHandle(String linkHandle) {
        this.linkHandle = linkHandle;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
