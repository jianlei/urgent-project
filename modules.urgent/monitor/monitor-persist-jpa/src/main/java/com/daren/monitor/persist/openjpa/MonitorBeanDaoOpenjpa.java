package com.daren.monitor.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.monitor.api.dao.IMonitorBeanDao;
import com.daren.monitor.entities.MonitorBean;


/**
 * Created by dell on 14-1-16.
 */
public class MonitorBeanDaoOpenjpa extends GenericOpenJpaDao<MonitorBean, Long> implements IMonitorBeanDao {
}
