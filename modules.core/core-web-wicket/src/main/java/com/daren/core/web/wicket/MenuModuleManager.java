package com.daren.core.web.wicket;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.api.module.IModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-1-24
 * Time: 下午6:47
 * To change this template use File | Settings | File Templates.
 */
public class MenuModuleManager {

    //菜单集合
    private List<IMenuItemsModule> menuItemModuleList = new ArrayList<IMenuItemsModule>();

    private List<IMenuModule> menuModuleList = new ArrayList<IMenuModule>();


    private static MenuModuleManager install;

    private MenuModuleManager() {

    }

    public synchronized static MenuModuleManager getInstall() {
        if (install == null) {
            install = new MenuModuleManager();
        }
        return install;
    }

    public List<IMenuModule> getMenuModuleList() {
        Collections.sort(menuModuleList, COMPARATOR);
        return menuModuleList;
    }

    public List<IMenuItemsModule> getMenuItemModuleList() {
        return menuItemModuleList;
    }

    public void add(IMenuModule menusModule) {
        menuModuleList.add(menusModule);
    }

    public void remove(IMenuModule menusModule) {
        menuModuleList.remove(menusModule);
    }

    public void add(IMenuItemsModule menuItemModule) {
        menuItemModuleList.add(menuItemModule);
    }

    public void remove(IMenuItemsModule menuItemModule) {
        menuItemModuleList.remove(menuItemModule);
    }


    /**
     * 通过tag获取对应的菜单模块
     *
     * @param tags
     * @return
     */
    public List<IModule> findMenusByTag(String... tags) {
        List<IModule> modules = new ArrayList<IModule>();

        //获取对应的tag的菜单
        if (menuItemModuleList != null) {
            for (IMenuItemsModule menusModule : menuItemModuleList) {
                for (String tag : tags) {
                    if (tag.equals(menusModule.getTag())) {
                        modules.add(menusModule);
                    }
                }
            }
        }

        //获取对应的tag的菜单目录
        if (menuModuleList != null) {
            for (IMenuModule directoryMenusModule : menuModuleList) {

                for (String tag : tags) {
                    if (tag.equals(directoryMenusModule.getTag())) {
                        modules.add(directoryMenusModule);
                    }
                }

            }
        }
        Collections.sort(modules, COMPARATOR);
        return modules;
    }

    //比较菜单的显示顺序
    private static Comparator<IModule> COMPARATOR = new Comparator<IModule>() {
        // This is where the sorting happens.
        public int compare(IModule o1, IModule o2) {
            return o1.getIndex() - o2.getIndex();
        }
    };


}
