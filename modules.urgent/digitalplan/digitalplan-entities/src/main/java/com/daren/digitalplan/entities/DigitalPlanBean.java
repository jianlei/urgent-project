package com.daren.digitalplan.entities;

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
@Table(name = "urg_digitalplan")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class DigitalPlanBean extends PersistentEntity {
    public DigitalPlanBean() {
    }
}
