package com.daren.reserveplan.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by dell on 14-1-16.
 * 专项预案
 */
@Entity
@Table(name = "biz_plan_special")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class SpecialPlanBean extends PersistentEntity {

    private String name;

    public SpecialPlanBean() {
        name="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
