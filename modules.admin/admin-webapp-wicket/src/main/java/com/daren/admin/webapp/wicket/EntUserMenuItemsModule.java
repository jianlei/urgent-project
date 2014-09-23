package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.EntUserListPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/23 15:16
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class EntUserMenuItemsModule implements IMenuItemsModule {

    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 0;
    }


    @Override
    public String getName() {
        return "企业用户管理";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "enterprise.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new EntUserListPage(id, wmc);
    }

}
