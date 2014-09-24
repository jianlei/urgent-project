package com.daren.core.web.api.module;

import org.apache.wicket.markup.html.WebPage;

/**
 * @类描述：系统菜单的子菜单
 * @创建人：xukexin
 * @创建时间：2014/9/23 8:12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface ISystemItemModule extends IModule{
    /**
     * 获得返回的页面
     * @return
     */
    WebPage getWebPage();
    /**
     * wicket application name
     * @return
     */
    String getProjectName();

    /**
     * 获得系统的url路径
     * @return
     */
    String getUrl();
}
