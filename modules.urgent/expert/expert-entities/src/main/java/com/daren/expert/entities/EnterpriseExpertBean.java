package com.daren.expert.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * 企业专家
 * Created by 张清欣 on 14-7-28.
 * @修改人：dlw
 * @修改时间：2014-08-27 上午10:17
 * @修改备注：增加属性="" 不然前台页面的做大量判断
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_exp_enterprise")
@XmlRootElement
public class EnterpriseExpertBean extends PersistentEntity {
    private long attach;    //专家归属（专家是哪个企业的）
    private String name ="";    //专家姓名
    private Date date;  //出生日期
    private String sex  =""; //性别
    private String skillTitle  ="";  //技术职称
    private String degree  ="";  //学位
    private String nation ="";  //民族
    private String type ="";    //专家类别
    private String city ="";    //所在城市
    private String address =""; //通信地址
    private String tel =""; //单位电话
    private String phone ="";   //手机
    private String eMail ="";   //邮箱
    private String language ="";    //外语语种
    private String domain ="";  //技术领域
    private String direction ="";   //研究方向
    private String jd ="";   //经度
    private String wd ="";    //纬度

    public EnterpriseExpertBean(){

    }

    public long getAttach() {
        return attach;
    }

    public void setAttach(long attach) {
        this.attach = attach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

}
