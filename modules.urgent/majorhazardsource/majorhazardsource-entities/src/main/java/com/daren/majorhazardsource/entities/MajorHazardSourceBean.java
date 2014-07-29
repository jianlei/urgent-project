package com.daren.majorhazardsource.entities;

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
@Table(name = "urgent_majorhazardsource")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class MajorHazardSourceBean extends PersistentEntity {

    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 评估
     */
    private String estimate;
    /**
     * 危险源名称
     */
    private String name;
    /**
     * 专家名称
     */
    private String expertName;
    /**
     * 事故率
     */
    private int accidentRate;

    public MajorHazardSourceBean() {

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
}
