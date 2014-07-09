package com.daren.enterprise.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.enterprise.api.dao.IEnterpriseBeanDao;
import com.daren.enterprise.entities.EnterpriseBeanImpl;


/**
 * Created by dell on 14-1-16.
 */
public class EnterpriseBeanDaoOpenjpa extends GenericOpenJpaDao<EnterpriseBeanImpl, Long> implements IEnterpriseBeanDao {

}
