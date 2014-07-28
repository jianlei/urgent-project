package com.daren.accident.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.entities.AccidentBeanImpl;


/**
 * Created by dell on 14-1-16.
 */
public class AccidentBeanDaoOpenjpa extends GenericOpenJpaDao<AccidentBeanImpl, Long> implements IAccidentBeanDao {

}
