package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IRoleBeanDao;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * @类描述：角色dao实现类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午6:32
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RoleBeanDaoOpenjpa extends GenericOpenJpaDao<RoleBeanImpl, Long> implements IRoleBeanDao {
}
