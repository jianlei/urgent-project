package com.daren.core.web.wicket.custome;

import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.wicket.manager.MenuModuleManager;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.List;

/**
 * Created by dell on 14-3-23.
 */
public class CustomeMenuPanel extends Panel {
    public CustomeMenuPanel(String id, WebMarkupContainer wmc) {
        super(id);
        List<IMenuModule> menuList = MenuModuleManager.getInstall().getMenuModuleMap(getApplication().getName());
        RepeatingView listItems = new RepeatingView("menuItems");
        if (menuList != null) {
            int i = 0;
            for (IMenuModule menu : menuList) {
                listItems.add(new CustomeMenuItemsPanel(listItems.newChildId(), menu, wmc, i));
                i++;
            }
        }
        add(listItems);
    }


}