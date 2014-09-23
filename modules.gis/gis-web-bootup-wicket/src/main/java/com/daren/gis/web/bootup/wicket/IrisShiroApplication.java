package com.daren.gis.web.bootup.wicket;


import com.daren.core.web.wicket.BaseWebApplication;
import com.daren.gis.webapp.wicket.page.GisTemplatePage;
import org.apache.wicket.Page;

public class IrisShiroApplication extends BaseWebApplication {
    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return GisTemplatePage.class;
    }

    @Override
    public void mountHomePage() {
        mountPage("/home/", GisTemplatePage.class);
    }
}
