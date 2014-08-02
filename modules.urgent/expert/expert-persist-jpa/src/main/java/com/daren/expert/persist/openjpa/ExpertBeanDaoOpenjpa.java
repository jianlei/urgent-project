package com.daren.expert.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.expert.api.dao.IExpertBeanDao;
import com.daren.expert.entities.ExpertBeanImpl;


/**
 * Created by dell on 14-1-16.
 */
public class ExpertBeanDaoOpenjpa extends GenericOpenJpaDao<ExpertBeanImpl, Long> implements IExpertBeanDao {

}
