package com.daren.core.web.bootup.wicket;


import com.daren.core.web.wicket.BaseWebApplication;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.Page;

public class IrisShiroApplication extends BaseWebApplication {
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
}
