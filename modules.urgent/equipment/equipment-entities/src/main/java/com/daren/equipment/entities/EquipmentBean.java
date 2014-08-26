package com.daren.equipment.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "urgent_equipment")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class EquipmentBean extends PersistentEntity {

    private String name;//物资装备名称
    private String property;//属性 ，设备1，耗材2
    private String registrationType;//登记类型 救援队装备1，社会装备2
    private String rescueId;//所属救援队
    private String unitName;//所属单位
    private String equipmentSources;//装备来源
    private String equipmentType;//物资类型
    private String parametersSpecifications;//参数规格
    private String measuringUnit;//计量单位
    private String amount;//数量
    private String regularMaintenanceInterval;//定期保修间隔
    private String durableYears;//使用年限
    private String lastMaintenanceDate;//上一次保养日期
    private String manufacturer;//生产厂家
    private String manufactureDate;//生产日期
    private String purchaseDate;//购买日期
    private String unitFax;//单位传真
    private String principal;//主要负责人
    private String officePhone;//办公电话
    private String homePhone;//家庭电话
    private String mobilePhone;//移动电话
    private String describeOrPurposes;//装备描述或装备用途
    private String warehouse;//存放的仓库名
    private String storagePlace;//存放场所
    private String img;//装备图片
    private String remark;//备注
    private String jd;//经度
    private String wd;//维度


    public EquipmentBean() {
        name = "";
        property = "";
        registrationType = "";
        rescueId = "";
        unitName = "";
        equipmentSources = "";
        equipmentType = "";
        parametersSpecifications = "";
        measuringUnit = "";
        amount = "";
        regularMaintenanceInterval = "";
        durableYears = "";
        lastMaintenanceDate = "";
        manufacturer = "";
        manufactureDate = "";
        purchaseDate = "";
        unitFax = "";
        principal = "";
        officePhone = "";
        homePhone = "";
        mobilePhone = "";
        describeOrPurposes = "";
        warehouse = "";
        storagePlace = "";
        img = "";
        remark = "";
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getRescueId() {
        return rescueId;
    }

    public void setRescueId(String rescueId) {
        this.rescueId = rescueId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getEquipmentSources() {
        return equipmentSources;
    }

    public void setEquipmentSources(String equipmentSources) {
        this.equipmentSources = equipmentSources;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getParametersSpecifications() {
        return parametersSpecifications;
    }

    public void setParametersSpecifications(String parametersSpecifications) {
        this.parametersSpecifications = parametersSpecifications;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRegularMaintenanceInterval() {
        return regularMaintenanceInterval;
    }

    public void setRegularMaintenanceInterval(String regularMaintenanceInterval) {
        this.regularMaintenanceInterval = regularMaintenanceInterval;
    }

    public String getDurableYears() {
        return durableYears;
    }

    public void setDurableYears(String durableYears) {
        this.durableYears = durableYears;
    }

    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getUnitFax() {
        return unitFax;
    }

    public void setUnitFax(String unitFax) {
        this.unitFax = unitFax;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDescribeOrPurposes() {
        return describeOrPurposes;
    }

    public void setDescribeOrPurposes(String describeOrPurposes) {
        this.describeOrPurposes = describeOrPurposes;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
