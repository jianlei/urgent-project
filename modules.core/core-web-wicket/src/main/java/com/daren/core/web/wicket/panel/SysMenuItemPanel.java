package com.daren.core.web.wicket.panel;

import com.daren.core.web.api.module.IModule;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.api.module.ISystemModule;
import com.daren.core.web.wicket.manager.SysMenuModuleManager;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/23 10:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SysMenuItemPanel extends Panel {

    static boolean isload = false;
    ISystemItemModule systemItemModule;

    public SysMenuItemPanel(String id, final ISystemModule systemModule, final WebMarkupContainer wmc, final int index) {
        super(id);
        Label labelName = new Label("name", systemItemModule.getName());
        add(labelName);

        //获取目录下面的子菜单
        List<IModule> submenus = SysMenuModuleManager.getInstall().findMenusByTag(systemModule.getTargetTag());
        ListView<IModule> listView = new ListView<IModule>("menuList", submenus) {         //对应SysMenuItemPanel
            @Override
            protected void populateItem(final ListItem<IModule> item) {
                Link submenusLink = new Link("menus") {
                    @Override
                    public void onClick() {
                        setResponsePage();
                    }                             //对应SysMenuItemPanel

                };
                submenusLink.setOutputMarkupId(true);
                submenusLink.add(new Label("linkName", item.getModelObject().getName()).setRenderBodyOnly(true).setEscapeModelStrings(false));
                item.add(submenusLink);
            }
        };
        add(listView);
    }
}
