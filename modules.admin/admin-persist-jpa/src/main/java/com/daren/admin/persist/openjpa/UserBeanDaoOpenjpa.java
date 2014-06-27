package com.daren.admin.persist.openjpa;

import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.impl.persistence.GenericOpenJpaDao;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 14-1-16.
 */
public class UserBeanDaoOpenjpa extends GenericOpenJpaDao<UserBeanImpl, Long> implements IUserBeanDao {
    private final String className = com.daren.admin.entities.UserBeanImpl.class.getName();

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserBeanDaoOpenjpa() {
//        super.setEntityManager(UserBeanImpl.class);
    }

    @Override
    public List<UserBeanImpl> getUserList() {
        log.debug("get user info");
        return super.getAll(className);
    }

    @Override
    public UserBeanImpl saveUser(UserBeanImpl user) {
        return super.save(user);
    }

    @Override
    public void removeUser(Long userId) {
        super.remove(className, userId);
    }

    @Override
    public UserBeanImpl getUser(Long userId) {
        return super.get(className, userId);
    }

    public UserBeanImpl getUser(String username) {
        UserBeanImpl user = this.findUnique("select u from UserBeanImpl u where u.loginName=?1", username);
        return user;
    }

    @Override
    public void updateUserLoginInfo(long id, String loginIp) {
        this.update("update UserBeanImpl u set u.loginIp=?1, u.loginDate=?2 where u.id = ?3", loginIp, new Date(), id);
    }
}
