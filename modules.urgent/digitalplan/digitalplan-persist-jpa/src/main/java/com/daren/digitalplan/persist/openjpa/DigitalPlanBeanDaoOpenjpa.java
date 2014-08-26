package com.daren.digitalplan.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.digitalplan.api.dao.IDigitalPlanBeanDao;
import com.daren.digitalplan.entities.DigitalPlanBean;


/**
 * Created by dell on 14-1-16.
 */
public class DigitalPlanBeanDaoOpenjpa extends GenericOpenJpaDao<DigitalPlanBean, Long> implements IDigitalPlanBeanDao {
}
