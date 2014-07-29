package com.daren.drill.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.drill.api.dao.IDocmentBeanDao;
import com.daren.drill.entities.DocmentBean;

/**
 * 应急演练管理-文档Dao实现
 * Created by 张清欣 on 14-7-28.
 */
public class DocmentBeanDaoOpenjpa extends GenericOpenJpaDao<DocmentBean, Long> implements IDocmentBeanDao {
}
