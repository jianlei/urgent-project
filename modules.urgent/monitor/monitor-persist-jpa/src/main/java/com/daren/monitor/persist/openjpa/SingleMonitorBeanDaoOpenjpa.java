package com.daren.monitor.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.monitor.api.dao.ISingleMonitorBeanDao;
import com.daren.monitor.entities.SingleMonitorBean;


/**
 * Created by dell on 14-1-16.
 */
public class SingleMonitorBeanDaoOpenjpa extends GenericOpenJpaDao<SingleMonitorBean, Long> implements ISingleMonitorBeanDao {
}
