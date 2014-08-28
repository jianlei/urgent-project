package com.daren.accident.persist.openjpa;

import com.daren.accident.api.dao.IAccidentBeanDao;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;


/**
 * Created by dell on 14-1-16.
 */
public class AccidentBeanDaoOpenjpa extends GenericOpenJpaDao<AccidentBean, Long> implements IAccidentBeanDao {


}
