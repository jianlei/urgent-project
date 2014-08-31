package com.daren.government.web.bootup.wicket.shiro;

import com.daren.admin.api.biz.IUserLoginService;
import com.daren.admin.entities.UserBean;
import com.daren.core.util.JNDIHelper;
import com.daren.core.web.wicket.PermissionConstant;
import com.daren.core.web.wicket.security.CaptchaException;
import com.daren.core.web.wicket.security.UsernamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.IOException;
import java.util.List;

/**
 * Simple shiro realm with only a few hard coded users
 *
 * @author Minto van der Sluis (misl)
 */
public class ShiroRealm extends AuthorizingRealm {
    private IUserLoginService userLoginService;

    // --------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------

    public ShiroRealm() {
        setName("exampleMemoryRealm");
        CredentialsMatcher cm = new SimpleCredentialsMatcher();
        setCredentialsMatcher(cm);

    }

    /**
     * 授权查询回调函数
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加用户权限
        try {
            userLoginService = (IUserLoginService) JNDIHelper.getJNDIServiceForName(IUserLoginService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> userPermission = userLoginService.getUserPermission(userName);
        if (userPermission != null) {
            for (String permission : userPermission)
                info.addStringPermission(permission);
        }

        return info;
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        try {
            userLoginService = (IUserLoginService) JNDIHelper.getJNDIServiceForName(IUserLoginService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String userName = (String) authToken.getPrincipal();
        char[] password = (char[]) authToken.getCredentials();
        // 判断验证码
        Session session = SecurityUtils.getSubject().getSession();
        String code = (String) session.getAttribute(PermissionConstant.VALIDATE_CODE);
        if (token.getCaptcha() == null || !token.getCaptcha().equalsIgnoreCase(code)) {
            throw new CaptchaException("验证码错误!");
        }
        UserBean userBean = userLoginService.login(userName, String.valueOf(password));
        if (userBean != null) {
            //保存用户信息到session
            session = SecurityUtils.getSubject().getSession();
            session.setAttribute(PermissionConstant.SYS_CURRENT_USER, userBean);
            userLoginService.updateUserLoginInfo(userBean.getId(), SecurityUtils.getSubject().getSession().getHost());
            return new SimpleAuthenticationInfo(userName, password, getName());
        }
        throw new AuthenticationException();
    }

}