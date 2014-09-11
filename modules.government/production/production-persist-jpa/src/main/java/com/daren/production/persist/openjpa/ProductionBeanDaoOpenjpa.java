package com.daren.production.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.production.api.dao.IProductionBeanDao;
import com.daren.production.entities.ProductionBean;

/**
 * Created by Administrator on 2014/9/10.
 */
public class ProductionBeanDaoOpenjpa extends GenericOpenJpaDao<ProductionBean, Long> implements IProductionBeanDao {
}
