package com.daren.admin.core;

import com.daren.admin.api.biz.IUserLoginService;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.PermissionBeanImpl;
import com.daren.admin.entities.RoleBeanImpl;
import com.daren.admin.entities.UserBeanImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 14-3-25.
 */

public class UserLoginServiceImpl implements IUserLoginService {
    //    @Named
    /*@Inject
    @Reference(id="userBeanDao",serviceInterface = IUserBeanDao.class)*/
    private IUserBeanDao userBeanDao;

    public void setUserBeanDao(IUserBeanDao userBeanDao) {
        this.userBeanDao = userBeanDao;
    }

    @Override
    public UserBeanImpl login(String username, String password) {
        UserBeanImpl user = userBeanDao.getUser(username);
        if (user == null) {
            return null;
        }
        if (encrypt(password).equals(user.getPassword())) {

            return user;
        } else {
            return null;
        }
    }

    @Override
    public void updateUserLoginInfo(long id, String loginIp) {
        userBeanDao.updateUserLoginInfo(id, loginIp);
    }

    @Override
    public List<String> getUserPermission(String username) {
        List<String> stringList = new ArrayList<String>();
        UserBeanImpl userBean = userBeanDao.getUser(username);
        if (userBean != null) {
            List<RoleBeanImpl> roleBeanList = userBean.getRoleList();
            for (RoleBeanImpl rolebean : roleBeanList) {
                for (PermissionBeanImpl permissionBean : rolebean.getPermissionList()) {
                    String permission = permissionBean.getPermission();
                    if (null != permission && !permission.equals(""))
                        stringList.add(permission);

                }
            }
        }
        return stringList;
    }

    private String encrypt(String text) {

        return text;
    }
}
