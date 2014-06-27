package com.daren.admin.api.biz;


import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.api.biz.IBizService;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUserBeanService extends IBizService {
    public UserBeanImpl addUser(UserBeanImpl user);

    public void delUser(UserBeanImpl user);

    public void saveUser(UserBeanImpl user);

    public List<UserBeanImpl> getAllUser();
}
