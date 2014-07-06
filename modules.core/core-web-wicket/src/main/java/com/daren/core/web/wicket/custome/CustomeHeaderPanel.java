package com.daren.core.web.wicket.custome;

import com.daren.admin.entities.UserBeanImpl;
import com.daren.core.web.wicket.PermissionConstant;
import com.daren.core.web.wicket.security.SignInPage;
import com.daren.core.web.wicket.security.SignOutPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by dell on 14-3-23.
 */
public class CustomeHeaderPanel extends Panel {
    public CustomeHeaderPanel(String id) {
        super(id);
        add(new Link("signout") {
            @Override
            public void onClick() {
                setResponsePage(SignOutPage.class);
            }
        });
        //添加用户姓名到页面
        //todo 获得当前用户需要封装到单独的service中
        String userName = "";
        Session session = SecurityUtils.getSubject().getSession();
        UserBeanImpl userBean = (UserBeanImpl) session.getAttribute(PermissionConstant.SYS_CURRENT_USER);
        if (userBean != null) {
            userName = userBean.getName();
        } else {
            SecurityUtils.getSubject().logout();
            setResponsePage(SignInPage.class);
        }
        Label userNameLabel = new Label("userName", userName);
        add(userNameLabel);
    }
}
