package com.daren.rescue.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @类描述：救援队管理
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：dlw
 * @修改时间：2014-08-27 上午10:17
 * @修改备注：增加属性="" 不然前台页面的做大量判断
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_res_rescue")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class RescueBean extends PersistentEntity {
    private long enterpriseId;  //企业ID
    private String name ="";    //救援队名称
    private String head ="";    //负责人
    private String headPhone ="";   //负责人电话
    private String telephone ="";   //值班电话
    private long totalNumber;   //总人数\
    private String address =""; //地址
    private String jd ="";   //经度
    private String wd ="";    //纬度
    private String equipment ="";   //主要装备描述
    private String expertise ="";   //专长描述
    private String remarks ="";  //备注

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeadPhone() {
        return headPhone;
    }

    public void setHeadPhone(String headPhone) {
        this.headPhone = headPhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @XmlAttribute
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}