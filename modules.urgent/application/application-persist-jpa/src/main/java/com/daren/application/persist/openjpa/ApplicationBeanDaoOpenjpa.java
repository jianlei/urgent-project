package com.daren.application.persist.openjpa;

import com.daren.application.entities.ApplicationBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.application.api.dao.IApplicationBeanDao;


/**
 * Created by dell on 14-1-16.
 */
public class ApplicationBeanDaoOpenjpa extends GenericOpenJpaDao<ApplicationBean, Long> implements IApplicationBeanDao {

}
