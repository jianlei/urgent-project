package com.daren.enterprise.persist.openjpa;

import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.enterprise.api.dao.IOrganizationBeanDao;
import com.daren.enterprise.entities.OrganizationBean;
/**
 * @类描述：cache implement class
 * @创建人：xukexin
 * @创建时间：2014/8/26
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class OrganizationBeanDaoOpenjap extends GenericOpenJpaDao<OrganizationBean, Long> implements IOrganizationBeanDao {
}
