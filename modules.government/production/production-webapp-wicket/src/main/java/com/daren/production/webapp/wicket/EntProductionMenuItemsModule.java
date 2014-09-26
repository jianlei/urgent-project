package com.daren.production.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.production.webapp.wicket.page.ProductionTabPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：企业用户安全生产许可证(非煤矿矿山企业)子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EntProductionMenuItemsModule implements IMenuItemsModule {


    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 50;
    }

    @Override
    public String getName() {
        return "安全生产许可证(非煤矿山)";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "ent.government.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
//        return new SingleCheckPage(id, wmc);
        return new ProductionTabPage(id, wmc, null);
    }
}