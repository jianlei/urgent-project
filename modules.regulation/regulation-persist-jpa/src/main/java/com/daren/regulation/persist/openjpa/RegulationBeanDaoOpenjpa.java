package com.daren.regulation.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.regulation.api.dao.IRegulationBeanDao;
import com.daren.regulation.entities.RegulationBean;


/**
 * Created by dell on 14-1-16.
 */
public class RegulationBeanDaoOpenjpa extends GenericOpenJpaDao<RegulationBean, Long> implements IRegulationBeanDao {

}
