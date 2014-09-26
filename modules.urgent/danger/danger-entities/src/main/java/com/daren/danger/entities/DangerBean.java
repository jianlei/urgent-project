package com.daren.danger.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * 危化品实体类
 * Created by dell on 14-1-16.
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_danger")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DangerBean extends PersistentEntity {

    /*private String jd="";//经度
    private String wd="";//纬度
    private String estimate="";//评估*/
    private String name="";//危险源名称
    /*private String expertName="";//专家名称
    private int accidentRate;//可能发生的事故率
    private String qyid;//企业ID
    private String place="";//所在地址
    private String startTime="";//投用时间
    private String level="";//重大危险源级别
    private String describe="";//主要装置，设置，规模
    private String inChemical="";//是否在化工园区
    private String distanceOtherHazard="";//距离其他重危目标距离
    private String scope500MHaveMans="";//范围500米内人数估算值
    private String lastThreeYearAccident="";//近三年事故
    private String roadConditions="";//道路情况

    private String zbqkd="";//周边情况（东）
    private String sfwhqyd="";//是否危化企业（东）
    private String jbqyzjzxjld="";//剧本企业最近直线距离（东）
    private String zbqklxd="";//情况类型（东）

    private String zbqkn="";//周边情况（南）
    private String sfwhqydn="";//是否危化企业（南）
    private String jbqyzjzxjln="";//剧本企业最近直线距离（南）
    private String zbqklxn="";//情况类型（南）

    private String zbqkx="";//周边情况（西）
    private String sfwhqyx="";//是否危化企业（西）
    private String jbqyzjzxjlx="";//剧本企业最近直线距离（西）
    private String zbqklxx="";//情况类型（西）

    private String zbqkb="";//周边情况（北）
    private String sfwhqyb="";//是否危化企业（北）
    private String jbqyzjzxjlb="";//剧本企业最近直线距离（北）
    private String zbqklxb="";//情况类型（北）

    private String yxfw="";//影响范围
    private String jbqk="";//基本情况
    private String zlcs="";//治理措施
    private String remark="";//备注*/


    public DangerBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
