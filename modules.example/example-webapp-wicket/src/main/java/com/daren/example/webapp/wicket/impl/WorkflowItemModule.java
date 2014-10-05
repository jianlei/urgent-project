package com.daren.example.webapp.wicket.impl;

import com.daren.core.web.api.module.IMenuItemsModule;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：工作流演示注册类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/15
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class WorkflowItemModule implements IMenuItemsModule {
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
        return 30;
    }

    @Override
    public String getName() {
        return "工作流例子";  //To change body of implemented methods use File | Settings | File Templates.
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
//        return new MainPage(id, wmc);
        return null;
    }
}