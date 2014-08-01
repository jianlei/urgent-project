package com.daren.admin.api.biz;


import com.daren.admin.entities.UserBean;
import com.daren.core.api.biz.IBizService;

import java.util.List;

/**
 * Created by dell on 14-1-17.
 */
public interface IUserBeanService extends IBizService {
    public UserBean addUser(UserBean user);

    public void delUser(UserBean user);

    public void saveUser(UserBean user);

    public List<UserBean> getAllUser();

    List<UserBean> queryUser(UserBean userBean);
}
