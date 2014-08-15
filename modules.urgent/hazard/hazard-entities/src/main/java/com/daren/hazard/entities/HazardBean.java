package com.daren.hazard.entities;

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
@Table(name = "urg_hazard")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class HazardBean extends PersistentEntity {

    private String lng;//经度
    private String lat;//纬度
    private String estimate;//评估
    private String name;//危险源名称
    private String expertName;//专家名称
    private int accidentRate;//可能发生的事故率
    private int enterpriseBeanId;//企业ID
    private String place;//所在地址
    private String startTime;//投用时间
    private String level;//重大危险源级别
    private String describe;//主要装置，设置，规模
    private String inChemical;//是否在化工园区
    private String distanceOtherHazard;//距离其他重危目标距离
    private String scope500MHaveMans;//范围500米内人数估算值
    private String lastThreeYearAccident;//近三年事故
    private String roadConditions;//道路情况

    private String zbqkd;//周边情况（东）
    private String sfwhqyd;//是否危化企业（东）
    private String jbqyzjzxjld;//剧本企业最近直线距离（东）
    private String zbqklxd;//情况类型（东）

    private String zbqkn;//周边情况（南）
    private String sfwhqydn;//是否危化企业（南）
    private String jbqyzjzxjln;//剧本企业最近直线距离（南）
    private String zbqklxn;//情况类型（南）

    private String zbqkx;//周边情况（西）
    private String sfwhqyx;//是否危化企业（西）
    private String jbqyzjzxjlx;//剧本企业最近直线距离（西）
    private String zbqklxx;//情况类型（西）

    private String zbqkb;//周边情况（北）
    private String sfwhqyb;//是否危化企业（北）
    private String jbqyzjzxjlb;//剧本企业最近直线距离（北）
    private String zbqklxb;//情况类型（北）

    private String yxfw;//影响范围
    private String jbqk;//基本情况
    private String zlcs;//治理措施
    private String remark;//备注


    public HazardBean() {

    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public int getAccidentRate() {
        return accidentRate;
    }

    public void setAccidentRate(int accidentRate) {
        this.accidentRate = accidentRate;
    }

    public int getEnterpriseBeanId() {
        return enterpriseBeanId;
    }

    public void setEnterpriseBeanId(int enterpriseBeanId) {
        this.enterpriseBeanId = enterpriseBeanId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getInChemical() {
        return inChemical;
    }

    public void setInChemical(String inChemical) {
        this.inChemical = inChemical;
    }

    public String getDistanceOtherHazard() {
        return distanceOtherHazard;
    }

    public void setDistanceOtherHazard(String distanceOtherHazard) {
        this.distanceOtherHazard = distanceOtherHazard;
    }

    public String getScope500MHaveMans() {
        return scope500MHaveMans;
    }

    public void setScope500MHaveMans(String scope500MHaveMans) {
        this.scope500MHaveMans = scope500MHaveMans;
    }

    public String getLastThreeYearAccident() {
        return lastThreeYearAccident;
    }

    public void setLastThreeYearAccident(String lastThreeYearAccident) {
        this.lastThreeYearAccident = lastThreeYearAccident;
    }

    public String getRoadConditions() {
        return roadConditions;
    }

    public void setRoadConditions(String roadConditions) {
        this.roadConditions = roadConditions;
    }

    public String getZbqkd() {
        return zbqkd;
    }

    public void setZbqkd(String zbqkd) {
        this.zbqkd = zbqkd;
    }

    public String getSfwhqyd() {
        return sfwhqyd;
    }

    public void setSfwhqyd(String sfwhqyd) {
        this.sfwhqyd = sfwhqyd;
    }

    public String getJbqyzjzxjld() {
        return jbqyzjzxjld;
    }

    public void setJbqyzjzxjld(String jbqyzjzxjld) {
        this.jbqyzjzxjld = jbqyzjzxjld;
    }

    public String getZbqklxd() {
        return zbqklxd;
    }

    public void setZbqklxd(String zbqklxd) {
        this.zbqklxd = zbqklxd;
    }

    public String getZbqkn() {
        return zbqkn;
    }

    public void setZbqkn(String zbqkn) {
        this.zbqkn = zbqkn;
    }

    public String getSfwhqydn() {
        return sfwhqydn;
    }

    public void setSfwhqydn(String sfwhqydn) {
        this.sfwhqydn = sfwhqydn;
    }

    public String getJbqyzjzxjln() {
        return jbqyzjzxjln;
    }

    public void setJbqyzjzxjln(String jbqyzjzxjln) {
        this.jbqyzjzxjln = jbqyzjzxjln;
    }

    public String getZbqklxn() {
        return zbqklxn;
    }

    public void setZbqklxn(String zbqklxn) {
        this.zbqklxn = zbqklxn;
    }

    public String getZbqkx() {
        return zbqkx;
    }

    public void setZbqkx(String zbqkx) {
        this.zbqkx = zbqkx;
    }

    public String getSfwhqyx() {
        return sfwhqyx;
    }

    public void setSfwhqyx(String sfwhqyx) {
        this.sfwhqyx = sfwhqyx;
    }

    public String getJbqyzjzxjlx() {
        return jbqyzjzxjlx;
    }

    public void setJbqyzjzxjlx(String jbqyzjzxjlx) {
        this.jbqyzjzxjlx = jbqyzjzxjlx;
    }

    public String getZbqklxx() {
        return zbqklxx;
    }

    public void setZbqklxx(String zbqklxx) {
        this.zbqklxx = zbqklxx;
    }

    public String getZbqkb() {
        return zbqkb;
    }

    public void setZbqkb(String zbqkb) {
        this.zbqkb = zbqkb;
    }

    public String getSfwhqyb() {
        return sfwhqyb;
    }

    public void setSfwhqyb(String sfwhqyb) {
        this.sfwhqyb = sfwhqyb;
    }

    public String getJbqyzjzxjlb() {
        return jbqyzjzxjlb;
    }

    public void setJbqyzjzxjlb(String jbqyzjzxjlb) {
        this.jbqyzjzxjlb = jbqyzjzxjlb;
    }

    public String getZbqklxb() {
        return zbqklxb;
    }

    public void setZbqklxb(String zbqklxb) {
        this.zbqklxb = zbqklxb;
    }

    public String getYxfw() {
        return yxfw;
    }

    public void setYxfw(String yxfw) {
        this.yxfw = yxfw;
    }

    public String getJbqk() {
        return jbqk;
    }

    public void setJbqk(String jbqk) {
        this.jbqk = jbqk;
    }

    public String getZlcs() {
        return zlcs;
    }

    public void setZlcs(String zlcs) {
        this.zlcs = zlcs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
