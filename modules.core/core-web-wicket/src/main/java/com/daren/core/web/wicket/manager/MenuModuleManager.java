package com.daren.core.web.wicket.manager;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.api.module.IModule;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-1-24
 * Time: 下午6:47
 * To change this template use File | Settings | File Templates.
 */
public class MenuModuleManager {

    private static MenuModuleManager install;
    //比较菜单的显示顺序
    private static Comparator<IModule> COMPARATOR = new Comparator<IModule>() {
        // This is where the sorting happens.
        public int compare(IModule o1, IModule o2) {
            return o1.getIndex() - o2.getIndex();
        }
    };
    //菜单集合
    private List<IMenuItemsModule> menuItemModuleList = new ArrayList<IMenuItemsModule>();
    private Map<String, List<IMenuModule>> menuModuleMap = new HashMap<>();

    private MenuModuleManager() {

    }

    public synchronized static MenuModuleManager getInstall() {
        if (install == null) {
            install = new MenuModuleManager();
        }
        return install;
    }

    public List<IMenuModule> getMenuModuleMap(String key) {
        List<IMenuModule> list = menuModuleMap.get(key);
        if(list!=null)
            Collections.sort(list, COMPARATOR);
        return list;
    }

    public List<IMenuItemsModule> getMenuItemModuleList() {
        return menuItemModuleList;
    }

    /**
     * 根据project name添加
     *
     * @param menusModule
     */
    public void add(IMenuModule menusModule) {
        List<IMenuModule> list;
        String name = menusModule.getProjectName();
        if (menuModuleMap.containsKey(name)) {
            list = menuModuleMap.get(name);
            list.add(menusModule);
        } else {
            list = new ArrayList<>();
            list.add(menusModule);
            menuModuleMap.put(name, list);
        }

    }

    public void remove(IMenuModule menusModule) {
        List<IMenuModule> list;
        String name = menusModule.getProjectName();
        list = menuModuleMap.get(name);
        list.remove(menusModule);
        if (list.size() == 0) {
            menuModuleMap.remove(name);
        }
    }

    public void add(IMenuItemsModule menuItemModule) {
        menuItemModuleList.add(menuItemModule);
    }

    public void remove(IMenuItemsModule menuItemModule) {
        menuItemModuleList.remove(menuItemModule);
    }

    /**
     * 通过tag获取对应的菜单模块
     * @param tag
     * @return
     */
    public List<IModule> findMenusByTag(String tag) {
        List<IModule> modules = new ArrayList<IModule>();

        //获取对应的tag的菜单
        if (menuItemModuleList != null) {
            for (IMenuItemsModule menusModule : menuItemModuleList) {
                    if (tag.equals(menusModule.getTag())) {
                        modules.add(menusModule);
                    }
            }
        }

        //获取对应的tag的菜单目录
        /*if (menuModuleMap != null) {
            List<IMenuModule> menuModuleList = menuModuleMap.get(key);
            for (IMenuModule directoryMenusModule : menuModuleList) {
                    if (tag.equals(directoryMenusModule.getTag())) {
                        modules.add(directoryMenusModule);
                    }
             }
        }*/
        Collections.sort(modules, COMPARATOR);
        return modules;
    }


}
