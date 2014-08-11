package com.daren.rescue.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.rescue.api.dao.ISchedulingBeanDao;
import com.daren.rescue.entities.SchedulingBean;

/**
 * Created by Administrator on 2014/8/11.
 */
public class SchedulingBeanDaoOpenjpa extends GenericOpenJpaDao<SchedulingBean, Long> implements ISchedulingBeanDao {
}
