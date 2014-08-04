package com.daren.reserveplan.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;


/**
 * Created by dell on 14-1-16.
 */
public class ReservePlanBeanDaoOpenjpa extends GenericOpenJpaDao<ReservePlanBean, Long> implements IReservePlanBeanDao {

}
