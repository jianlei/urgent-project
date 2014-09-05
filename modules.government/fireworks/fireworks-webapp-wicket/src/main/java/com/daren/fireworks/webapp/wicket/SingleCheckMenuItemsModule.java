package com.daren.fireworks.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：例子子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class SingleCheckMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 10;
    }

    @Override
    public String getName() {
        return "烟花爆竹经营许可证";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "government.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
//        return new SingleCheckPage(id, wmc);
        return null;
    }
}