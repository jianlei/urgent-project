package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.UserBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-1-16.
 */
public class UserBeanDaoOpenjpa extends GenericOpenJpaDao<UserBean, Long> implements IUserBeanDao {
    private final String className = UserBean.class.getName();

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserBeanDaoOpenjpa() {
//        super.setEntityManager(UserBean.class);
    }

    @Override
    public List<UserBean> getUserList() {
        log.debug("get user info");
        return super.getAll(className);
    }

    @Override
    public UserBean saveUser(UserBean user) {
        return super.save(user);
    }

    @Override
    public void removeUser(Long userId) {
        super.remove(className, userId);
    }

    @Override
    public UserBean getUser(Long userId) {
        return super.get(className, userId);
    }

    public UserBean getUser(String username) {
        UserBean user = this.findUnique("select u from UserBean u where u.loginName=?1", username);
        return user;
    }

    @Override
    public void updateUserLoginInfo(long id, String loginIp) {
        this.update("update UserBean u set u.loginIp=?1, u.loginDate=?2 where u.id = ?3", loginIp, new Date(), id);
    }
}
