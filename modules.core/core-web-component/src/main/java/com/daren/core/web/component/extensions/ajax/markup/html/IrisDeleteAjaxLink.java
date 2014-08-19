package com.daren.core.web.component.extensions.ajax.markup.html;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * 项目名称:  urgent-project
 * 类描述:   带删除确认消息提示框的删除按钮
 * 创建人:    sunlf
 * 创建时间:  2014/8/19 16:07
 * 修改人:    sunlf
 * 修改时间:  2014/8/19 16:07
 * 修改备注:  [说明本次修改内容]
 */
public abstract class IrisDeleteAjaxLink<T> extends AjaxLink {
    public IrisDeleteAjaxLink(String id) {
        super(id);
    }

    /**
     * 重载，提示消息框
     * @param attributes
     */
    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        AjaxCallListener listener = new AjaxCallListener();

        listener.onPrecondition("if(!confirm('" + getString("urgent.delete.confirm") + "')){return false;}");
        attributes.getAjaxCallListeners().add(listener);
    }
}
