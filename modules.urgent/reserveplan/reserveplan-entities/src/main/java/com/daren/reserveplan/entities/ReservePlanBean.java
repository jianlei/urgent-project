package com.daren.reserveplan.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "biz_plan_reserve")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ReservePlanBean extends PersistentEntity {

    private String name;
    private String description;
    private long reservePlanApplyId;
    private long reservePlanRegisterId;
    private long reviewCommentId;
    private long reservePlanDocumentId;
    private List expertList;

    public ReservePlanBean() {
        name="";
        description="";
        reservePlanApplyId=-1;
        reservePlanRegisterId=-1;
        reviewCommentId=-1;
        reservePlanDocumentId=-1;
        expertList = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
