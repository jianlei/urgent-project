package com.daren.majorhazardsource.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.majorhazardsource.webapp.wicket.page.MajorHazardSourcePage;
import com.daren.majorhazardsource.webapp.wicket.page.MajorHazardSourceTabPage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：品牌子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MajoHazardSourceMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 4;
    }

    @Override
    public String getName() {
        return "重大危险源管理";  //To change body of implemented methods use File | Settings | File Templates.
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
        return new MajorHazardSourceTabPage(id, wmc);
    }
}