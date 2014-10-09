package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IUserRelBeanDao;
import com.daren.admin.entities.UserRelBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/10/9 13:32
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class UserRelBeanDaoOpenjpa extends GenericOpenJpaDao<UserRelBean, Long> implements IUserRelBeanDao {

    private final String className = UserRelBean.class.getName();

    @Override
    public UserRelBean saveUserRel(UserRelBean userRelBean) {
        return super.save(userRelBean);
    }

    @Override
    public void removeUserRel(Long user_id) {
        super.remove(className,user_id);
    }
}
