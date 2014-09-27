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
public class DangerBean  extends PersistentEntity{

    private String name;
    private String chinese_name=""; //中文名称
    private String chinese_otherName;  //中文别名
    private String english_name;//英文名称
    private String English_OtherName="";//英文别名
    private String CAS_Num="";//cas编号
    private String Formula="";//分子式
    private String Formula_Weight="";//分子量
    private String Physical_property="";//理化特性识别知识
    private String Shape="";//外形与形状
    private String Melting_Point="";//熔点
    private String Temperature="";//
    private String Tolerance="";//相对气度
    private String Boiling_Point="";//沸点
    private String Air_Pressure="";//
    private String critical_pressure="";//临界压力
    private String density_ratio="";//相对密度
    private String burn_Hot="";//燃烧热
    private String flash_point="";//闪点
    private String nature_temperature="";//自燃温度
    private String explode_upper="";//燃烧上限
    private String explode_below="";//燃烧下限
    private String harm_type="";//
    private String harm_health="";//健康危害
    private String first_aid="";//急救措施
    private String fire_control="";//消防措施
    private String release_measures="";//泄漏应急处理
    private String control_setting="";//操作设置
    private String touch_control="";//接触控制
    private String stability="";//稳定性和反应活性
    private String toxicology_data="";//毒性
    private String transport_information="";//运输信息


    public DangerBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public String getChinese_otherName() {
        return chinese_otherName;
    }

    public void setChinese_otherName(String chinese_otherName) {
        this.chinese_otherName = chinese_otherName;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getEnglish_OtherName() {
        return English_OtherName;
    }

    public void setEnglish_OtherName(String english_OtherName) {
        English_OtherName = english_OtherName;
    }

    public String getCAS_Num() {
        return CAS_Num;
    }

    public void setCAS_Num(String CAS_Num) {
        this.CAS_Num = CAS_Num;
    }

    public String getFormula() {
        return Formula;
    }

    public void setFormula(String formula) {
        Formula = formula;
    }

    public String getFormula_Weight() {
        return Formula_Weight;
    }

    public void setFormula_Weight(String formula_Weight) {
        Formula_Weight = formula_Weight;
    }

    public String getPhysical_property() {
        return Physical_property;
    }

    public void setPhysical_property(String physical_property) {
        Physical_property = physical_property;
    }

    public String getShape() {
        return Shape;
    }

    public void setShape(String shape) {
        Shape = shape;
    }

    public String getMelting_Point() {
        return Melting_Point;
    }

    public void setMelting_Point(String melting_Point) {
        Melting_Point = melting_Point;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getTolerance() {
        return Tolerance;
    }

    public void setTolerance(String tolerance) {
        Tolerance = tolerance;
    }

    public String getBoiling_Point() {
        return Boiling_Point;
    }

    public void setBoiling_Point(String boiling_Point) {
        Boiling_Point = boiling_Point;
    }

    public String getAir_Pressure() {
        return Air_Pressure;
    }

    public void setAir_Pressure(String air_Pressure) {
        Air_Pressure = air_Pressure;
    }

    public String getCritical_pressure() {
        return critical_pressure;
    }

    public void setCritical_pressure(String critical_pressure) {
        this.critical_pressure = critical_pressure;
    }

    public String getDensity_ratio() {
        return density_ratio;
    }

    public void setDensity_ratio(String density_ratio) {
        this.density_ratio = density_ratio;
    }

    public String getBurn_Hot() {
        return burn_Hot;
    }

    public void setBurn_Hot(String burn_Hot) {
        this.burn_Hot = burn_Hot;
    }

    public String getFlash_point() {
        return flash_point;
    }

    public void setFlash_point(String flash_point) {
        this.flash_point = flash_point;
    }

    public String getNature_temperature() {
        return nature_temperature;
    }

    public void setNature_temperature(String nature_temperature) {
        this.nature_temperature = nature_temperature;
    }

    public String getExplode_upper() {
        return explode_upper;
    }

    public void setExplode_upper(String explode_upper) {
        this.explode_upper = explode_upper;
    }

    public String getExplode_below() {
        return explode_below;
    }

    public void setExplode_below(String explode_below) {
        this.explode_below = explode_below;
    }

    public String getHarm_type() {
        return harm_type;
    }

    public void setHarm_type(String harm_type) {
        this.harm_type = harm_type;
    }

    public String getHarm_health() {
        return harm_health;
    }

    public void setHarm_health(String harm_health) {
        this.harm_health = harm_health;
    }

    public String getFirst_aid() {
        return first_aid;
    }

    public void setFirst_aid(String first_aid) {
        this.first_aid = first_aid;
    }

    public String getFire_control() {
        return fire_control;
    }

    public void setFire_control(String fire_control) {
        this.fire_control = fire_control;
    }

    public String getRelease_measures() {
        return release_measures;
    }

    public void setRelease_measures(String release_measures) {
        this.release_measures = release_measures;
    }

    public String getControl_setting() {
        return control_setting;
    }

    public void setControl_setting(String control_setting) {
        this.control_setting = control_setting;
    }

    public String getTouch_control() {
        return touch_control;
    }

    public void setTouch_control(String touch_control) {
        this.touch_control = touch_control;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public String getToxicology_data() {
        return toxicology_data;
    }

    public void setToxicology_data(String toxicology_data) {
        this.toxicology_data = toxicology_data;
    }

    public String getTransport_information() {
        return transport_information;
    }

    public void setTransport_information(String transport_information) {
        this.transport_information = transport_information;
    }
}
