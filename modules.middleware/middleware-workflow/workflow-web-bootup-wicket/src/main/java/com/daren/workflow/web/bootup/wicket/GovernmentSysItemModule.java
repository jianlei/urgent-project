package com.daren.workflow.web.bootup.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by xukexin on 2014-09-26.
 */
public class GovernmentSysItemModule  implements ISystemItemModule {
    @Override
    public WebPage getWebPage() {
        return new TemplatePage();
    }

    @Override
    public String getProjectName() {
        return IConst.GOVERNMENT_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getUrl() {
        return "../../government";
    }

    @Override
    public String getName() {
        return "政务管理";
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
        return 100;
    }
}
