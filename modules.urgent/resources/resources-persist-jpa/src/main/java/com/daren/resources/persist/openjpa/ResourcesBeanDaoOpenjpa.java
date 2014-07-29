package com.daren.resources.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.resources.api.dao.IResourcesBeanDao;
import com.daren.resources.entities.ResourcesBeanImpl;


/**
 * Created by dell on 14-1-16.
 */
public class ResourcesBeanDaoOpenjpa extends GenericOpenJpaDao<ResourcesBeanImpl, Long> implements IResourcesBeanDao {

}
