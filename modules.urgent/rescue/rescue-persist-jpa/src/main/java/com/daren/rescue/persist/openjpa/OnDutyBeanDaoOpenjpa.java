package com.daren.rescue.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.rescue.api.dao.IOnDutyBeanDao;
import com.daren.rescue.entities.OnDutyBean;

/**
 * Created by Administrator on 2014/8/8.
 */
public class OnDutyBeanDaoOpenjpa extends GenericOpenJpaDao<OnDutyBean, Long> implements IOnDutyBeanDao {
}
