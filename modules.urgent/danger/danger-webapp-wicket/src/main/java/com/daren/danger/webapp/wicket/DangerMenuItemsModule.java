package com.daren.danger.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.danger.webapp.wicket.page.DangerTabPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：危化品子菜单定义类
 * @创建人：xukexin
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DangerMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPermissionAvaliable() {
        return false;
    }

    @Override
    public String getPermissionName() {
        return null;
    }

    @Override
    public int getIndex() {
        return 6;
    }

    @Override
    public String getName() {
        return "危化品理化特征";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "plan.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new DangerTabPage(id, wmc);
    }
}