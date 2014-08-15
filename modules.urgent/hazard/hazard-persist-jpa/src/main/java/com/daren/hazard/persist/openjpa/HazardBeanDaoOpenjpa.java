package com.daren.hazard.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.hazard.api.dao.IHazardBeanDao;
import com.daren.hazard.entities.HazardBean;


/**
 * Created by dell on 14-1-16.
 */
public class HazardBeanDaoOpenjpa extends GenericOpenJpaDao<HazardBean, Long> implements IHazardBeanDao {
}
