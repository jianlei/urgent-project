package com.daren.accident.core.model;

/**
 * @类描述：用于json返回的数据模型
 * @创建人： sunlingfeng
 * @创建时间：2014/8/28
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AccidentJson {
    private String place;//事故发生地点
    private String accidentTitle;//事故标题
    private String detailsPlace;//详细地点
    private String accidentUnit;//事故单位 uuid
    private String jd;//经度
    private String wd;//纬度
    private String qymc;//企业名称
    private String accidentType;//事故类别
    private String accidentLevel;//事故级别
    private String videoLink;//视频链接地址
    private String operator;//经办人
    private String operatorPhone;//经办人电话

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAccidentTitle() {
        return accidentTitle;
    }

    public void setAccidentTitle(String accidentTitle) {
        this.accidentTitle = accidentTitle;
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

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public String getAccidentLevel() {
        return accidentLevel;
    }

    public void setAccidentLevel(String accidentLevel) {
        this.accidentLevel = accidentLevel;
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
}
