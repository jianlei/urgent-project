package com.daren.competency.persist.openjpa;

import com.daren.competency.api.dao.ICompetencyBeanDao;
import com.daren.competency.entities.CompetencyBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * Created by Administrator on 2014/9/10.
 */
public class CompetencyBeanDaoOpenjpa extends GenericOpenJpaDao<CompetencyBean, Long> implements ICompetencyBeanDao {
}
