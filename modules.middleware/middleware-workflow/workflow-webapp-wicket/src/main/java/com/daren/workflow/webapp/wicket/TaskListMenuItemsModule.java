package com.daren.workflow.webapp.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.workflow.webapp.wicket.page.TaskListPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：代办任务子菜单定义类
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:23
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class TaskListMenuItemsModule implements IMenuItemsModule {


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
        return "待办任务";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIcon() {
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTag() {
        return "process.module.bundles";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getPanel(String id, WebMarkupContainer wmc) {
        return new TaskListPage(id, wmc);
    }
}