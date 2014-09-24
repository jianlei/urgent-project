package com.daren.admin.web.bootup.wicket;

import com.daren.core.api.IConst;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.wicket.TemplatePage;
import org.apache.wicket.markup.html.WebPage;

/**
 * @类描述：系统菜单Admin子菜单
 * @创建人：xukexin
 * @创建时间：2014/9/23 8:19
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AdminSysItemModule implements ISystemItemModule{
    @Override
    public String getProjectName() {
        return IConst.SYSTEM_WICKET_APPLICATION_NAME;
    }

    @Override
    public String getUrl() {
        return "../../sys";
    }

    @Override
    public WebPage getWebPage(){
        return  new TemplatePage();
    }

    @Override
    public String getName() {
        return "系统管理";
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
        return 10;
    }

}
