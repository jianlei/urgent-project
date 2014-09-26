package com.daren.danger.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.danger.api.dao.IDangerBeanDao;
import com.daren.danger.entities.DangerBean;


/**
 * Created by dell on 14-1-16.
 */
public class DangerBeanDaoOpenjpa extends GenericOpenJpaDao<DangerBean, Long> implements IDangerBeanDao {
}
