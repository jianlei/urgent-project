package com.daren.rescue.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @类描述：救援队管理-值班
 * @创建人：张清欣
 * @创建时间：2014-08-08 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_res_onduty")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class OnDutyBean extends PersistentEntity {
    private long attach;    //归属(哪个救援队)
    private Date date;  //值班日期
    private String person;  //值班人员
    private String tel;     //值班电话
    private String expertise;   //专长描述
    private String remarks; //备注
    private String filePath;    //路径

    public long getAttach() {
        return attach;
    }

    public void setAttach(long attach) {
        this.attach = attach;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}