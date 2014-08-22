package com.daren.core.web.api.module;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 2014-3-23
 * Time: 下午12:40
 * 菜单模块（此菜单下面包含多个菜单MenuItems模块）
 */
public interface IMenuModule extends IModule {

    /**
     * wicket application name
     * @return
     */
    public String getProjectName();

    /**
     * 获取可以附加到这个菜单下的item的tag
     *
     * @return
     */
    public String getTargetTag();

}
