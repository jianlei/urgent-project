package com.daren.admin.webapp.wicket;

import com.daren.admin.webapp.wicket.page.AreaListPage;
import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：区域子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-08-07 下午11:03
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class AreaMenuItemsModule implements IMenuItemsModule {

    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 15;
    }


    @Override
    public String getName() {
        return "区域管理";  //To change body of implemented methods use File | Settings | File Templates.
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
        return new AreaListPage(id, wmc);
    }
}