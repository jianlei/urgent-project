package com.daren.core.web.wicket.security;

import com.daren.admin.api.biz.IUserLoginService;
import com.daren.admin.entities.UserBean;
import com.daren.core.util.JNDIHelper;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import java.io.IOException;

/**
 * Created by dell on 14-3-24.
 */
public class BasicAuthenticationSession extends AuthenticatedWebSession {
    /* @Named
     @Inject
     @Reference(id="userLoginService",serviceInterface = IUserLoginService.class)*/
    private IUserLoginService userLoginService;
    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    //    public void setUserLoginService(IUserLoginService userLoginService) {
//        this.userLoginService = userLoginService;
//    }

    public BasicAuthenticationSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(String username, String password) {
        try {
            userLoginService = (IUserLoginService) JNDIHelper.getJNDIServiceForName(IUserLoginService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userBean = userLoginService.login(username, password,0);
        return (userBean != null) ? true : false;

    }

    @Override
    public Roles getRoles() {
//        if (userBean != null)
//            return new Roles(userBean.getRole());
//        else
        return null;
    }
}
