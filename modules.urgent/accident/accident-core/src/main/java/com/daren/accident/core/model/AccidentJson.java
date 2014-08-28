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

    private String qymc;

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
}
