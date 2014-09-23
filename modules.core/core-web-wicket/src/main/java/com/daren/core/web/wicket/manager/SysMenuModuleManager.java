package com.daren.core.web.wicket.manager;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IModule;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.api.module.ISystemModule;

import java.util.*;

/**
 * @类描述：系统级菜单控制
 * @创建人：xukexin
 * @创建时间：2014/9/23 11:03
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SysMenuModuleManager {

    private static SysMenuModuleManager install;
    //比较菜单的显示顺序
    private static Comparator<IModule> COMPARATOR = new Comparator<IModule>() {
        // This is where the sorting happens.
        public int compare(IModule o1, IModule o2) {
            return o1.getIndex() - o2.getIndex();
        }
    };

    private List<ISystemItemModule> systemItemModulesList = new ArrayList<ISystemItemModule>();      //子菜单集合
    private Map<String, List<ISystemModule>> sysModuleMap = new HashMap<>();      //包含顶级菜单（List<IMenuModule>），和每类菜单对应的key

    private SysMenuModuleManager() {

    }
    //创建线程安全的实例
    public synchronized static SysMenuModuleManager getInstall() {
        if (install == null) {
            install = new SysMenuModuleManager();
        }
        return install;
    }
    //通过key获取一级菜单项并排序
    public List<ISystemModule> getSystemModuleMap(String key) {
        List<ISystemModule> list = sysModuleMap.get(key);
        if(list!=null)
            Collections.sort(list, COMPARATOR);
        return list;
    }
    //获取子菜单集合
    public List<ISystemItemModule> getSystemItemModulesList() {
        return systemItemModulesList;
    }

    /**
     * 根据name添加
     * @param systemModule
     */
    public void add(ISystemModule systemModule) {
        List<ISystemModule> list;
        String name = systemModule.getName(); //menusModule.getProjectName();
        if (sysModuleMap.containsKey(name)) {       //如果已包含改菜单
            list = sysModuleMap.get(name);
            list.add(systemModule);
        } else {
            list = new ArrayList<>();               //如果未包含
            list.add(systemModule);
            sysModuleMap.put(name, list);
        }

    }

    /**
     * 根据name移除
     * @param systemModule
     */
    public void remove(ISystemModule systemModule) {
        List<ISystemModule> list;
        String name = systemModule.getName();       //.getProjectName();
        list = sysModuleMap.get(name);
        list.remove(systemModule);
        if (list.size() == 0) {
            sysModuleMap.remove(name);
        }
    }
    //添加子菜单项
    public void add(ISystemItemModule systemItemModule) {
        systemItemModulesList.add(systemItemModule);
    }
    //移除子菜单项
    public void remove(ISystemItemModule systemItemModule) {
        systemItemModulesList.remove(systemItemModule);
    }

    /**
     * 通过tag获取对应的菜单模块
     * @param tag
     * @return
     */
    public List<IModule> findMenusByTag(String tag) {
        List<IModule> modules = new ArrayList<IModule>();
        //获取对应的tag的菜单
        if (systemItemModulesList != null) {
            for (IMenuItemsModule menusModule : systemItemModulesList) {
                if (tag.equals(menusModule.getTag())) {
                    modules.add(menusModule);
                }
            }
        }
        Collections.sort(modules, COMPARATOR);
        return modules;
    }

}
