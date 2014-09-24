package com.daren.core.web.wicket.panel;

import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.api.module.ISystemModule;
import com.daren.core.web.wicket.manager.SysMenuModuleManager;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/23 10:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SysMenuItemsPanel extends Panel {

    static boolean isload = false;

    public SysMenuItemsPanel(String id, final ISystemModule systemModule, final int index) {
        super(id);
        Label labelName = new Label("sysName", systemModule.getName());
        add(labelName.setRenderBodyOnly(true));

        //获取目录下面的子菜单
        List<ISystemItemModule> submenus = SysMenuModuleManager.getInstall().findMenusByTag(systemModule.getTargetTag());
        //去掉本身系统的链接
        List<ISystemItemModule> filterMenus = new ArrayList<>();
        for (ISystemItemModule systemItemModule : submenus) {
            if (!getApplication().getName().equals(systemItemModule.getProjectName())) {
                filterMenus.add(systemItemModule);
            }
        }
        ListView<ISystemItemModule> listView = new ListView<ISystemItemModule>("menuList", filterMenus) {         //对应SysMenuItemPanel
            @Override
            protected void populateItem(final ListItem<ISystemItemModule> item) {
                {
                    final ISystemItemModule systemItemModule = item.getModelObject();
                    Link link = new Link("menus") {
                        @Override
                        protected CharSequence getURL() {

                            return systemItemModule.getUrl();
                        }

                        @Override
                        public void onClick() {
                            //setResponsePage(item.getWebPage());
                        }                             //对应SysMenuItemPanel
                    };

                    link.setOutputMarkupId(true);
                    link.add(new Label("linkName", systemItemModule.getName()).setRenderBodyOnly(true).setEscapeModelStrings(false));
                    item.add(link);
                }

            }
        };
        add(listView);
    }
}
