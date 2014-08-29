package com.daren.example.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.example.webapp.wicket.page.JScriptTest;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/9 19:53
 * 修改人:    sunlf
 * 修改时间:  2014/7/9 19:53
 * 修改备注:  [说明本次修改内容]
 */
public class JScriptMenuItemModule implements IMenuItemsModule {
    @Override
    public String getNo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndex() {
        return 20;
    }

    @Override
    public String getName() {
        return "JScript例子";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "example.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new JScriptTest(id, wmc);
    }
}
