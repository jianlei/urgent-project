package com.daren.core.web.wicket.panel;

import com.daren.admin.entities.UserBean;
import com.daren.core.web.wicket.PermissionConstant;
import com.daren.core.web.wicket.security.LockScreen;
import com.daren.core.web.wicket.security.SignOutPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by dell on 14-3-23.
 */
public class HeaderPanel extends Panel {
    public HeaderPanel(String id) {
        super(id);
        add(new Link("signout") {
            @Override
            public void onClick() {
//we redirect browser to another page.
                setResponsePage(SignOutPage.class);
            }
        });
        //添加用户姓名到页面
        //todo 获得当前用户需要封装到单独的service中
        String userName = "";
        Session session = SecurityUtils.getSubject().getSession();
        UserBean userBean = (UserBean) session.getAttribute(PermissionConstant.SYS_CURRENT_USER);
        if (userBean != null) {
            userName = userBean.getName();
        } else {
           /* SecurityUtils.getSubject().logout();
            setResponsePage(SignInPage.class);*/
        }
        Label userNameLabel = new Label("userName", userName);
        add(userNameLabel);


        add(new Link("lockScreen") {
            @Override
            public void onClick() {
//we redirect browser to another page.
                setResponsePage(LockScreen.class);
            }
        });
    }
}
