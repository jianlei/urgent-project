package com.daren.accident.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "urg_accident")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class AccidentBean extends PersistentEntity {


    private String place;//事故发生地点
    private String accidentTitle;//事故标题
    private String detailsPlace;//详细地点
    private String accidentUnit;//事故单位
    private String accidentPreliminaryAnalysis;//事故原因初步分析
    private String accidentSource;//事故源
    private Date accidentTime;//事故发生时间
    private String cause;//原因
    private String toll;//伤亡人数
    private String economicLosses;//直接经济损失
    private String accidentScene;//事故现场总人数
    private String headcountDeath;//事故伤亡人数-死亡
    private String headcountSerious;//事故伤亡人数-重伤
    private String headcountSlight;//事故伤亡人数-轻伤
    private String headcountTrappedOrMissing;//事故伤亡人数-被困或下落不明
    private String headcountEvacuees;//事故伤亡人数-被疏散人数
    private String lng;//经度
    private String lat;//纬度
    private String accidentType;//事故类别
    private String industryCategory;//行业类别
    private String accidentLevel;//事故级别
    private String accidentDescribe;//事故现场情况、事故简要经过
    private String measure;//已采取措施
    private String attachment;//附件
    private String videoLink;//视频链接地址
    private String operator;//经办人
    private String operatorPhone;//经办人电话
    private String liaisons;//现场联络人
    private String liaisonsPhone;//现场联络人电话
    private String signer;//签发人

    public AccidentBean() {
    }

    public String getAccidentTitle() {
        return accidentTitle;
    }

    public void setAccidentTitle(String accidentTitle) {
        this.accidentTitle = accidentTitle;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetailsPlace() {
        return detailsPlace;
    }

    public void setDetailsPlace(String detailsPlace) {
        this.detailsPlace = detailsPlace;
    }

    public String getAccidentUnit() {
        return accidentUnit;
    }

    public void setAccidentUnit(String accidentUnit) {
        this.accidentUnit = accidentUnit;
    }

    public String getAccidentPreliminaryAnalysis() {
        return accidentPreliminaryAnalysis;
    }

    public void setAccidentPreliminaryAnalysis(String accidentPreliminaryAnalysis) {
        this.accidentPreliminaryAnalysis = accidentPreliminaryAnalysis;
    }

    public String getAccidentSource() {
        return accidentSource;
    }

    public void setAccidentSource(String accidentSource) {
        this.accidentSource = accidentSource;
    }

    public Date getAccidentTime() {
        return accidentTime;
    }

    public void setAccidentTime(Date accidentTime) {
        this.accidentTime = accidentTime;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getToll() {
        return toll;
    }

    public void setToll(String toll) {
        this.toll = toll;
    }

    public String getEconomicLosses() {
        return economicLosses;
    }

    public void setEconomicLosses(String economicLosses) {
        this.economicLosses = economicLosses;
    }

    public String getAccidentScene() {
        return accidentScene;
    }

    public void setAccidentScene(String accidentScene) {
        this.accidentScene = accidentScene;
    }

    public String getHeadcountDeath() {
        return headcountDeath;
    }

    public void setHeadcountDeath(String headcountDeath) {
        this.headcountDeath = headcountDeath;
    }

    public String getHeadcountSerious() {
        return headcountSerious;
    }

    public void setHeadcountSerious(String headcountSerious) {
        this.headcountSerious = headcountSerious;
    }

    public String getHeadcountSlight() {
        return headcountSlight;
    }

    public void setHeadcountSlight(String headcountSlight) {
        this.headcountSlight = headcountSlight;
    }

    public String getHeadcountTrappedOrMissing() {
        return headcountTrappedOrMissing;
    }

    public void setHeadcountTrappedOrMissing(String headcountTrappedOrMissing) {
        this.headcountTrappedOrMissing = headcountTrappedOrMissing;
    }

    public String getHeadcountEvacuees() {
        return headcountEvacuees;
    }

    public void setHeadcountEvacuees(String headcountEvacuees) {
        this.headcountEvacuees = headcountEvacuees;
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

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    public String getAccidentLevel() {
        return accidentLevel;
    }

    public void setAccidentLevel(String accidentLevel) {
        this.accidentLevel = accidentLevel;
    }

    public String getAccidentDescribe() {
        return accidentDescribe;
    }

    public void setAccidentDescribe(String accidentDescribe) {
        this.accidentDescribe = accidentDescribe;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getLiaisons() {
        return liaisons;
    }

    public void setLiaisons(String liaisons) {
        this.liaisons = liaisons;
    }

    public String getLiaisonsPhone() {
        return liaisonsPhone;
    }

    public void setLiaisonsPhone(String liaisonsPhone) {
        this.liaisonsPhone = liaisonsPhone;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }
}
