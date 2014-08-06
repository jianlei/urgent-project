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
@Table(name = "biz_plan_comprehensive")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ComprehensivePlanBean extends PersistentEntity {

    private String name;

    public ComprehensivePlanBean() {
        name="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
