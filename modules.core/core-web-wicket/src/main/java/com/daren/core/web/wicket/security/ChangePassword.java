package com.daren.core.web.wicket.security;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.Arrays;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    修改用户密码
 * 创建人:    sunlf
 * 创建时间:  2014/8/1 23:39
 * 修改人:    sunlf
 * 修改时间:  2014/8/1 23:39
 * 修改备注:  [说明本次修改内容]
 */
public abstract class ChangePassword extends AbstractFormDialog<PasswordInfo> {
    private static final long serialVersionUID = 1L;
    protected final DialogButton btnSubmit = new DialogButton("Save", JQueryIcon.CHECK);
    protected final DialogButton btnCancel = new DialogButton(LBL_CANCEL, JQueryIcon.CANCEL);

    private Form<?> form;
    private FeedbackPanel feedback;

    public ChangePassword(String id, String title) {
        super(id, title, true);

        this.form = new Form<Integer>("form");
        this.add(this.form);

        // Slider //
        this.form.add(new PasswordTextField("oldPassword"));
        this.form.add(new PasswordTextField("newPassword"));
        this.form.add(new PasswordTextField("repeatPassword"));

        // FeedbackPanel //
        this.feedback = new JQueryFeedbackPanel("feedback");
        this.form.add(this.feedback);
    }

    // AbstractFormDialog //
    @Override
    protected List<DialogButton> getButtons() {
        return Arrays.asList(this.btnSubmit, this.btnCancel);
    }

    @Override
    protected DialogButton getSubmitButton() {
        return this.btnSubmit;
    }

    @Override
    public Form<?> getForm() {
        return this.form;
    }

    @Override
    public void setModelObject(PasswordInfo passwordInfo) {
        this.setDefaultModel(new CompoundPropertyModel<PasswordInfo>(passwordInfo));
    }

    // Events //
    @Override
    protected void onOpen(AjaxRequestTarget target) {
        target.add(this.form);
    }

    @Override
    public void onError(AjaxRequestTarget target) {
        target.add(this.feedback);
    }
}

