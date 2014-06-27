package com.daren.core.web.api.module;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 2014-3-23
 * Time: 下午2:23
 * 模块接口
 */
public interface IModule {
    /**
     * 模块名称
     * @return
     */
    String getName();

    /**
     * 模块图标
     * @return
     */
    String getIcon();

    /**
     * 模块tag
     * @return
     */
    String getTag();

    /**
     * 子菜单的排列顺序
     *
     * @return
     */
    public int getIndex();

}
