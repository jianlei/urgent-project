package com.daren.gis.webapp.wicket.page.model;

/**
 * @类描述：用于接收gis返回的需要打印的物资json对象
 * @创建人： sunlingfeng
 * @创建时间：2014/8/30
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ResourceJson {
    private String[] expert;//专家
    private String[] rescue;//救援队
    private String[] equipment;//物资
    private String[] reserve;//企业预案
    private String[] digital;//安监预案

    public String[] getExpert() {
        return expert;
    }

    public void setExpert(String[] expert) {
        this.expert = expert;
    }

    public String[] getRescue() {
        return rescue;
    }

    public void setRescue(String[] rescue) {
        this.rescue = rescue;
    }

    public String[] getEquipment() {
        return equipment;
    }

    public void setEquipment(String[] equipment) {
        this.equipment = equipment;
    }

    public String[] getReserve() {
        return reserve;
    }

    public void setReserve(String[] reserve) {
        this.reserve = reserve;
    }

    public String[] getDigital() {
        return digital;
    }

    public void setDigital(String[] digital) {
        this.digital = digital;
    }
}
