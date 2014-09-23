package com.daren.core.web.wicket.listener;

import com.daren.core.web.api.module.ISystemItemModule;
import com.daren.core.web.api.module.ISystemModule;
import com.daren.core.web.wicket.manager.SysMenuModuleManager;
import org.apache.log4j.Logger;

/**
 * @类描述：系统菜单监听
 * @创建人：xukexin
 * @创建时间：2014/9/23 13:17
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SysMenuPanelListener {

    private static Logger logger = Logger.getLogger(MenuModuleListener.class);

    /**
     * 监听到系统菜单
     *
     * @param systemModule
     */
    public void bind(ISystemModule systemModule) {
        logger.info("sys module of " + systemModule.getName() + " is bound!");
        SysMenuModuleManager.getInstall().add(systemModule);
    }

    /**
     * 系统菜单被移除
     *
     * @param systemModule
     */
    public void unbind(ISystemModule systemModule) {
        logger.info("sys module of " + systemModule.getName() + " is unbound!");
        SysMenuModuleManager.getInstall().remove(systemModule);
    }
    /**
     * 监听到系统子菜单
     * @param systemItemModule
     */
    public void bind(ISystemItemModule systemItemModule) {
        logger.info("sys item module of " + systemItemModule.getName() + " is bound!");
        SysMenuModuleManager.getInstall().add(systemItemModule);
    }

    /**
     * 系统子菜单被移除
     * @param systemItemModule
     */
    public void unbind(ISystemItemModule systemItemModule) {
        logger.info("sys item module of " + systemItemModule.getName() + " is unbound!");
        SysMenuModuleManager.getInstall().remove(systemItemModule);
    }
}
