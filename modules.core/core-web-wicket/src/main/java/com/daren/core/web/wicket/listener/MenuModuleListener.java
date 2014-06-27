package com.daren.core.web.wicket.listener;


import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.wicket.MenuModuleManager;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-1-24
 * Time: 下午6:17
 * 菜单监听者
 */
public class MenuModuleListener {
    private static Logger logger = Logger.getLogger(MenuModuleListener.class);

    /**
     * 监听到菜单目录
     *
     * @param menuModule
     */
    public void bind(IMenuModule menuModule) {
        logger.info("menu module of " + menuModule.getName() + " is binded!");
        MenuModuleManager.getInstall().add(menuModule);

    }


    /**
     * 菜单目录被移除
     *
     * @param menuModule
     */
    public void unbind(IMenuModule menuModule) {
        logger.info("menu module of " + menuModule.getName() + " is unbound!");
        MenuModuleManager.getInstall().remove(menuModule);
    }


    /**
     * 监听到菜单
     *
     * @param menuItemsModule
     */
    public void bind(IMenuItemsModule menuItemsModule) {
        logger.info("menu item module of " + menuItemsModule.getName() + " is bound!");
        MenuModuleManager.getInstall().add(menuItemsModule);

    }

    /**
     * 菜单被移除
     *
     * @param menuItemsModule
     */
    public void unbind(IMenuItemsModule menuItemsModule) {
        logger.info("menu item module of " + menuItemsModule.getName() + " is unbound!");
        MenuModuleManager.getInstall().remove(menuItemsModule);

    }


}
