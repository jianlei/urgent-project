package com.daren.admin.core;

import com.daren.admin.api.biz.IUserLoginService;
import com.daren.admin.api.dao.IUserBeanDao;
import com.daren.admin.entities.PermissionBean;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;

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
    public UserBean login(String username, String password) {
        UserBean user = userBeanDao.getUser(username);
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
        UserBean userBean = userBeanDao.getUser(username);
        if (userBean != null) {
            List<RoleBean> roleBeanList = userBean.getRoleList();
            for (RoleBean rolebean : roleBeanList) {
                for (PermissionBean permissionBean : rolebean.getPermissionList()) {
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
