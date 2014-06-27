
package com.daren.core.web.api.module;

import org.apache.wicket.Page;

/**
 * 可以显示内容的模块
 */
public interface IPageModule extends IModule {
    Class<? extends Page> getPageClass();
}
