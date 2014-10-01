package com.daren.core.web.component.extensions.ajax.markup.html;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.model.IModel;

/**
 * 项目名称:  urgent-project
 * 类描述:    使用Jquery Ui样式的带等待效果的AjaxLink
 * 创建人:    sunlf
 * 创建时间:  2014/8/16 21:44
 * 修改人:    sunlf
 * 修改时间:  2014/8/16 21:44
 * 修改备注:  [说明本次修改内容]
 */
public abstract class IrisIndicatingAjaxLink extends IndicatingAjaxLink {
    public IrisIndicatingAjaxLink(String id) {
        super(id);
    }

    public IrisIndicatingAjaxLink(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(new Button.ButtonBehavior(IJQueryWidget.JQueryWidget.getSelector(this) /* [, options | icon]  */));
    }

}
