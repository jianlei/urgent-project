package com.daren.core.web.wicket.manager;

import com.daren.core.web.api.module.IModule;
import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.api.module.ISystemModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private List<ISystemItemModule> sysItemModulesList = new ArrayList<ISystemItemModule>();      //子菜单集合
    private List<ISystemModule> sysModuleList = new ArrayList<>();      //包含顶级菜单（List<IMenuModule>），和每类菜单对应的key

    private SysMenuModuleManager() {

    }
    //创建线程安全的实例
    public synchronized static SysMenuModuleManager getInstall() {
        if (install == null) {
            install = new SysMenuModuleManager();
        }
        return install;
    }

    //获取一级菜单项并排序
    public List<ISystemModule> getSystemModuleMap() {
        if(sysModuleList!=null)
            Collections.sort(sysModuleList, COMPARATOR);
        return sysModuleList;
    }


    /**
     * 添加
     * @param systemModule
     */
    public void add(ISystemModule systemModule) {
        if(!sysModuleList.contains(systemModule))
            sysModuleList.add(systemModule);
    }

    /**
     * 移除
     * @param systemModule
     */
    public void remove(ISystemModule systemModule) {
        if(sysModuleList.contains(systemModule)){
            sysItemModulesList.remove(systemModule);
        }
    }
    //添加子菜单项
    public void add(ISystemItemModule systemItemModule) {
        sysItemModulesList.add(systemItemModule);
    }
    //移除子菜单项
    public void remove(ISystemItemModule systemItemModule) {
        sysItemModulesList.remove(systemItemModule);
    }

    /**
     * 通过tag获取对应的菜单模块
     * @param tag
     * @return
     */
    public List<ISystemItemModule> findMenusByTag(String tag) {
        List<ISystemItemModule> modules = new ArrayList<>();
        //获取对应的tag的菜单
        if (sysItemModulesList != null) {
            for (ISystemItemModule menusModule : sysItemModulesList) {
                if (tag.equals(menusModule.getTag())) {
                    modules.add(menusModule);
                }
            }
        }
        Collections.sort(modules, COMPARATOR);
        return modules;
    }

}
