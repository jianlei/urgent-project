package com.daren.majorhazardsource.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.majorhazardsource.api.dao.IHazardBeanDao;
import com.daren.majorhazardsource.entities.HazardBean;


/**
 * Created by dell on 14-1-16.
 */
public class HazardBeanDaoOpenjpa extends GenericOpenJpaDao<HazardBean, Long> implements IHazardBeanDao {
}
