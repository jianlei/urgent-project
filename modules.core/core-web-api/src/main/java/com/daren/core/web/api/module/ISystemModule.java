package com.daren.core.web.api.module;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-3-23
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */
public interface ISystemModule extends IModule {

    /**
     * 获取可以附加到这个菜单下的item的tag
     *
     * @return
     */
    public String getTargetTag();
}
