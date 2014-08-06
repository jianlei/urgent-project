package com.daren.reserveplan.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.reserveplan.api.dao.IReservePlanBeanDao;
import com.daren.reserveplan.api.dao.ISpotPlanBeanDao;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.daren.reserveplan.entities.SpotPlanBean;


/**
 * Created by dell on 14-1-16.
 */
public class SpotPlanBeanDaoOpenjpa extends GenericOpenJpaDao<SpotPlanBean, Long> implements ISpotPlanBeanDao {

}
