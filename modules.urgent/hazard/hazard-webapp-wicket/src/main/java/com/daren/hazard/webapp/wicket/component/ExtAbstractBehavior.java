package com.daren.hazard.webapp.wicket.component;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/7/13 0:17
 * 修改人:    sunlf
 * 修改时间:  2014/7/13 0:17
 * 修改备注:  [说明本次修改内容]
 */
public class ExtAbstractBehavior extends AbstractAjaxBehavior {
    @Override
    public void onRequest() {

    }

    @Override
    protected void onComponentRendered() {
        super.onComponentRendered();
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

    }

    @Override
    public void beforeRender(Component component) {
        super.beforeRender(component);
        component.getResponse().write("test");
    }
}
