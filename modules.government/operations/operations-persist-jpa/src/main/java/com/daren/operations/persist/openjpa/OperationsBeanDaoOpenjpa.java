package com.daren.operations.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.operations.api.dao.IOperationsBeanDao;
import com.daren.operations.entities.OperationsBean;

/**
 * Created by Administrator on 2014/9/10.
 */
public class OperationsBeanDaoOpenjpa extends GenericOpenJpaDao<OperationsBean, Long> implements IOperationsBeanDao {
}
