package com.daren.core.web.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

/**
 * Created by dell on 14-3-23.
 */
//@AuthorizeInstantiation({"user","admin"})
@ShiroSecurityConstraint(constraint = ShiroConstraint.LoggedIn)
//@ShiroSecurityConstraints({@ShiroSecurityConstraint(constraint= ShiroConstraint.HasRole, value="user"),@ShiroSecurityConstraint(constraint= ShiroConstraint.HasRole, value="admin")})
public class HomePage extends BasePage {

    public HomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onConfigure() {
       /* AuthenticatedWebApplication app = (AuthenticatedWebApplication) AuthenticatedWebApplication.get();
        if (!AuthenticatedWebSession.get().isSignedIn())
            app.restartResponseAtSignInPage();*/
    }
}
