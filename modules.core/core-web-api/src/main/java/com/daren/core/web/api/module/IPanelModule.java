
package com.daren.core.web.api.module;


import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * 可以显示Panel的模块
 */
public interface IPanelModule extends IModule {
    Panel getPanel(String id, WebMarkupContainer wmc);
}
