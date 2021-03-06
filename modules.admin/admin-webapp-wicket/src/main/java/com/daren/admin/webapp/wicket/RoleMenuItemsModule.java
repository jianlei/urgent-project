package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.RoleListPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：角色子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:44
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class RoleMenuItemsModule implements IMenuItemsModule {
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
        return Const.PERMISSION_ADMIN_ROLE;
    }

    @Override
    public int getIndex() {
        return Const.INDEX_ADMIN_ROLE;
    }


    @Override
    public String getName() {
        return Const.MENU_ADMIN_ROLE;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "admin.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new RoleListPage(id, wmc);
    }
}
