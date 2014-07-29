package com.daren.reserveplan.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBeanImpl;


/**
 * Created by dell on 14-1-16.
 */
public class ReservePlanBeanDaoOpenjpa extends GenericOpenJpaDao<ReservePlanBeanImpl, Long> implements IReservePlanBeanDao {

}
