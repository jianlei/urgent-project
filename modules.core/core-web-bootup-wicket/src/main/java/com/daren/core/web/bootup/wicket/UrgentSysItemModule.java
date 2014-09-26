package com.daren.core.web.bootup.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by xukexin on 2014-09-26.
 */
public class UrgentSysItemModule implements ISystemItemModule {
    @Override
    public WebPage getWebPage() {
        return new TemplatePage();
    }

    @Override
    public String getProjectName() {
        return IConst.URGENT_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getUrl() {
        return "../../urgent";
    }

    @Override
    public String getName() {
        return "应急管理";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getTag() {
        return IConst.PROJECT_WICKET_APPLICATION_NAME;
    }

    @Override
    public int getIndex() {
        return 101;
    }
}
