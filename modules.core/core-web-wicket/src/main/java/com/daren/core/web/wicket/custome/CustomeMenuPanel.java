package com.daren.core.web.wicket.custome;

import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.wicket.manager.MenuModuleManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.List;

/**
 * @类描述：左侧主菜单界面类
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:44
 * @修改人：sunlf
 * @修改时间：2014-10-4
 * @修改备注：增加权限控制
 */
public class CustomeMenuPanel extends Panel {
    public CustomeMenuPanel(String id, WebMarkupContainer wmc) {
        super(id);
        List<IMenuModule> menuList = MenuModuleManager.getInstall().getMenuModuleMap(getApplication().getName());
        RepeatingView listItems = new RepeatingView("menuItems");
        if (menuList != null) {
            int i = 0;
            Subject currentUser = SecurityUtils.getSubject();
            for (IMenuModule menu : menuList) {
                if(menu.isPermissionAvaliable()){//权限检查

                    if(currentUser.isPermitted(menu.getPermissionName())){
                        listItems.add(new CustomeMenuItemsPanel(listItems.newChildId(), menu, wmc, i));
                        i++;
                    }
                }
                else
                {
                    listItems.add(new CustomeMenuItemsPanel(listItems.newChildId(), menu, wmc, i));
                    i++;
                }
            }
        }
        add(listItems);
    }


}