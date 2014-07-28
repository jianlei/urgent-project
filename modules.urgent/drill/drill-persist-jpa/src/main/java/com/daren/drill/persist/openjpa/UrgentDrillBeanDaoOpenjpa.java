package com.daren.drill.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.drill.api.dao.IUrgentDrillBeanDao;
import com.daren.drill.entities.UrgentDrillBean;

/**
 * 应急演练管理Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class UrgentDrillBeanDaoOpenjpa extends GenericOpenJpaDao<UrgentDrillBean, Long> implements IUrgentDrillBeanDao {
}
