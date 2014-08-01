package com.daren.admin.core;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.UserBean;
import com.daren.core.impl.biz.GenericBizServiceImpl;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public class UserBeanServiceImpl extends GenericBizServiceImpl implements IUserBeanService {
    private IUserBeanDao userBeanDao;

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
        super.init(userBeanDao, UserBean.class.getName());
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

    public UserBean addUser(UserBean user) {
        return userBeanDao.saveUser(user);
    }

    @Override
    public void delUser(UserBean user) {
        userBeanDao.removeUser(user.getId());
    }

    @Override
    public void saveUser(UserBean user) {
        userBeanDao.saveUser(user);
    }

    public List<UserBean> getAllUser() {
        return userBeanDao.getAll(UserBean.class.getName());
    }

    @Override
    public List<UserBean> queryUser(UserBean userBean) {

        return userBeanDao.find("select a from UserBean a where a.name LIKE ?1", "%" + userBean.getName() + "%");
    }


}
