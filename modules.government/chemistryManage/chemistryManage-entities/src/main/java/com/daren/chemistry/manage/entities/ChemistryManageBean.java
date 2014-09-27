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
    private String qyName;//企业名称
    private String qyCode;//企业编号
    private String address;//注册地址
    private String phone;//联系电话
    private String fax;//传真
    private String zipCode;//邮编
    private String qyType;//企业类型
    private String illegalPerson;//非法人类别
    private String specialType;//特别类型
    private String economicsNature;//经济性质
    private String directorUnits;//主管单位
    private String registrationAuthority;//登记机关
    private String mainHead;//主要负责人
    private String mainHeadId;//身份证号码(主要负责人)
    private String chargeHead;//分管负责人
    private String chargeHeadId;//身份证号码(分管负责人)
    private String workersNumber;//职工人数
    private String technologyNumber;//技术管理人数
    private String safetyNumber;//安全管理人数
    private String registrationCapital;//注册资本
    private String fixedAssets;//固定资产
    private String yearSale;//上年销售额
    private String manageAddress;//经营场所地址
    private String manageProperty;//经营场所产权
    private String storageAddress;//储存设施地址
    private String storageProperty;//储存设施产权
    private String buildingStructure;//建筑结构
    private String storageCapacity;//储存能力
    private String systemName;//主要管理制度名称
    private String communication;//通讯地址
    private String  mode;//经营方式
    private String unitType;//经营单位类型
    private String scope;//许可经营范围

    private String code;//登记编号
    private Date startDate;//有效日期开始
    private Date endDate;//有效日期结束
    private String unitsDate;//发证机关

    private String linkHandle;//当前办理环节
    private String loginName;//申请人登陆名
    private String proposerId;//审请人ID
    private String qyid;//企业ID

    public String getQyName() {
        return qyName;
    }

    public void setQyName(String qyName) {
        this.qyName = qyName;
    }

    public String getQyCode() {
        return qyCode;
    }

    public void setQyCode(String qyCode) {
        this.qyCode = qyCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getQyType() {
        return qyType;
    }

    public void setQyType(String qyType) {
        this.qyType = qyType;
    }

    public String getIllegalPerson() {
        return illegalPerson;
    }

    public void setIllegalPerson(String illegalPerson) {
        this.illegalPerson = illegalPerson;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getEconomicsNature() {
        return economicsNature;
    }

    public void setEconomicsNature(String economicsNature) {
        this.economicsNature = economicsNature;
    }

    public String getDirectorUnits() {
        return directorUnits;
    }

    public void setDirectorUnits(String directorUnits) {
        this.directorUnits = directorUnits;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority;
    }

    public String getMainHead() {
        return mainHead;
    }

    public void setMainHead(String mainHead) {
        this.mainHead = mainHead;
    }

    public String getMainHeadId() {
        return mainHeadId;
    }

    public void setMainHeadId(String mainHeadId) {
        this.mainHeadId = mainHeadId;
    }

    public String getChargeHead() {
        return chargeHead;
    }

    public void setChargeHead(String chargeHead) {
        this.chargeHead = chargeHead;
    }

    public String getChargeHeadId() {
        return chargeHeadId;
    }

    public void setChargeHeadId(String chargeHeadId) {
        this.chargeHeadId = chargeHeadId;
    }

    public String getWorkersNumber() {
        return workersNumber;
    }

    public void setWorkersNumber(String workersNumber) {
        this.workersNumber = workersNumber;
    }

    public String getTechnologyNumber() {
        return technologyNumber;
    }

    public void setTechnologyNumber(String technologyNumber) {
        this.technologyNumber = technologyNumber;
    }

    public String getSafetyNumber() {
        return safetyNumber;
    }

    public void setSafetyNumber(String safetyNumber) {
        this.safetyNumber = safetyNumber;
    }

    public String getRegistrationCapital() {
        return registrationCapital;
    }

    public void setRegistrationCapital(String registrationCapital) {
        this.registrationCapital = registrationCapital;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getYearSale() {
        return yearSale;
    }

    public void setYearSale(String yearSale) {
        this.yearSale = yearSale;
    }

    public String getManageAddress() {
        return manageAddress;
    }

    public void setManageAddress(String manageAddress) {
        this.manageAddress = manageAddress;
    }

    public String getManageProperty() {
        return manageProperty;
    }

    public void setManageProperty(String manageProperty) {
        this.manageProperty = manageProperty;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getStorageProperty() {
        return storageProperty;
    }

    public void setStorageProperty(String storageProperty) {
        this.storageProperty = storageProperty;
    }

    public String getBuildingStructure() {
        return buildingStructure;
    }

    public void setBuildingStructure(String buildingStructure) {
        this.buildingStructure = buildingStructure;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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