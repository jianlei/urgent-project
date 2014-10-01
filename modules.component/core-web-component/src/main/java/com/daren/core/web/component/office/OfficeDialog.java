package com.daren.core.web.component.office;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/8/25.
 */
public class OfficeDialog extends AbstractDialog {
    public OfficeDialog(String id, String title) {
        super(id, title);
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
        b.add(new DialogButton("关闭") {

        });
        return b;
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
