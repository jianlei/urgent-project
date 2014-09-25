package com.daren.company.web.bootup.wicket;


import com.daren.company.webapp.wicket.page.SignInPage;
import com.daren.core.web.wicket.BaseWebApplication;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.Page;

public class IrisShiroApplication extends BaseWebApplication {

    public IrisShiroApplication() {
    }

    @Override
    public Class<? extends Page> getLoginPage() {
        return  SignInPage.class;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return TemplatePage.class;
    }

    @Override
    public void mountHomePage() {
        mountPage("/home/", TemplatePage.class);
    }

    @Override
    public void mountLoginPage() {
        mountPage("login", SignInPage.class);
    }
}

