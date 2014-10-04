package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.EntUserListPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：企业用户菜单类
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
    public boolean isPermissionAvaliable() {
        return true;
    }

    @Override
    public String getPermissionName() {
        return Const.PERMISSION_ADMIN_ENT_USER;
    }

    @Override
    public int getIndex() {
        return Const.INDEX_ADMIN_ENT_USER;
    }


    @Override
    public String getName() {
        return Const.MENU_ADMIN_ENT_USER;  //To change body of implemented methods use File | Settings | File Templates.
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
