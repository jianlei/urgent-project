package com.daren.core.web.wicket.panel;

import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.api.module.ISystemModule;
import com.daren.core.web.wicket.manager.SysMenuModuleManager;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.List;

/**
 * @类描述：
 * @创建人：xukexin
 * @创建时间：2014/9/23 10:09
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SysMenuPanel extends Panel {
    public SysMenuPanel(String id) {
        super(id);
        //List<IMenuModule> menuList = MenuModuleManager.getInstall().getMenuModuleMap(getApplication().getName());
        List<ISystemModule> menuList = SysMenuModuleManager.getInstall().getSystemModuleMap("");        //通过key获取要显示的系统一级菜单
        RepeatingView listItems = new RepeatingView("menuItems");           //对应SysMenuPanel.html
        if (menuList != null) {
            int i = 0;
            for (IMenuModule menu : menuList) {
                listItems.add(new SysMenuItemPanel(listItems.newChildId(), menu, wmc, i));
                i++;
            }
        }
        add(listItems);
    }
}
