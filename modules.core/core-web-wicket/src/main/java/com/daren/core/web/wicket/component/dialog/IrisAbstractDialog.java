package com.daren.core.web.wicket.component.dialog;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/7 9:10
 * 修改人:    sunlf
 * 修改时间:  2014/8/7 9:10
 * 修改备注:  [说明本次修改内容]
 */
public class IrisAbstractDialog<T extends Serializable> extends AbstractDialog {
    public IrisAbstractDialog(String id, String title, IModel<T> model) {
        super(id, title, model);
    }


    /**
     * 设置自动显示为真
     * 设置esc键关闭弹出框为假
     *
     * @param behavior
     */
    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior);
        behavior.setOption("autoOpen", true);
        behavior.setOption("closeOnEscape", false);
    }

    /**
     * 默认按钮为关闭按钮
     *
     * @return
     */
    @Override
    protected List<DialogButton> getButtons() {
        List<DialogButton> b = new ArrayList<DialogButton>();
        b.add(new DialogButton("关闭"));
        return b; //this syntax is allowed until the button state (enable and/or visible) is not altered
    }

    /**
     * 使弹出框隐藏
     *
     * @param target
     * @param button
     */
    @Override
    public void onClose(AjaxRequestTarget target, DialogButton button) {
        this.setVisible(false);
        updateTarget(target);
    }

    /**
     * 使弹出框右上角的X按钮产生关闭事件
     *
     * @return
     */
    @Override
    public boolean isDefaultCloseEventEnabled() {
        return true;
    }

    @Override
    public void setModelObject(Serializable object) {
        this.setDefaultModel(new CompoundPropertyModel<>(object));
    }

    //更新父页面列表
    public void updateTarget(AjaxRequestTarget target) {
    }
}
