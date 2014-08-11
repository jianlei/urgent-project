package com.daren.reserveplan.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by dell on 14-1-16.
 *现场预案
 */
@Entity
@Table(name = "biz_plan_spot")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class SpotPlanBean extends PersistentEntity {

    private String name;
    private String description;
    private String type;
    private String spotPlanDocumentId;
    private long reservePlanId;

    public SpotPlanBean() {
        name="";
        description="";
        type="";
        spotPlanDocumentId="";
        reservePlanId=-1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpotPlanDocumentId() {
        return spotPlanDocumentId;
    }

    public void setSpotPlanDocumentId(String spotPlanDocumentId) {
        this.spotPlanDocumentId = spotPlanDocumentId;
    }

    public long getReservePlanId() {
        return reservePlanId;
    }

    public void setReservePlanId(long reservePlanId) {
        this.reservePlanId = reservePlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
