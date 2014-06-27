package com.daren.admin.core;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public class UserBeanServiceImpl extends GenericBizServiceImpl implements IUserBeanService {
    private IUserBeanDao userBeanDao;

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
        super.init(userBeanDao, com.daren.admin.entities.UserBeanImpl.class.getName());
    }

    public void init() {
        /*UserBeanImpl user = new UserBeanImpl();
        user.setName("test");
        user.setPassword("hello");
        user = addUser(user);
        List<UserBeanImpl> list = this.getAllUser();

        System.out.print("system is called " + list.size());*/
    }

    @Override

    public UserBeanImpl addUser(UserBeanImpl user) {
        return userBeanDao.saveUser(user);
    }

    @Override
    public void delUser(UserBeanImpl user) {
        userBeanDao.removeUser(user.getId());
    }

    @Override
    public void saveUser(UserBeanImpl user) {
        userBeanDao.saveUser(user);
    }

    public List<UserBeanImpl> getAllUser() {
        return userBeanDao.getAll(com.daren.admin.entities.UserBeanImpl.class.getName());
    }


}
