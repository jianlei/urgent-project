package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * 项目名称:  urgent-project
 * 类描述:    用户新增或修改页面
 * 创建人:    sunlf
 * 创建时间:  2014/8/5 13:44
 * 修改人:    sunlf
 * 修改时间:  2014/8/5 13:44
 * 修改备注:  [说明本次修改内容]
 */
public class UserAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    @Inject
    @Reference(id = "userBeanService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;
    private FeedbackPanel feedbackPanel; //信息显示

    public UserAddPage(String id, String type, IModel<UserBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
            //new model
            initForm(Model.of(new UserBean()));
        else
            //edit model
            initForm(model);


    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<UserBean> model) {
        final Form<UserBean> userForm = new Form("userForm", new CompoundPropertyModel(model));

        feedbackPanel = new FeedbackPanel("feedback");
        userForm.add(feedbackPanel.setOutputMarkupId(true));

        userForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        userForm.add(new TextField("loginName").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        userForm.add(new TextField("email").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        userForm.add(new TextField("phone"));
        userForm.add(new TextField("mobile").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
//      userForm.add(new TextField("loginDate").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        userForm.add(new AjaxButton("save", userForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    UserBean userBean = (UserBean) form.getModelObject();
                    userBeanService.saveEntity(userBean);
                    userForm.setModelObject(new UserBean());
                    feedbackPanel.info(type + userBean.getName() + "成功！");
                    target.add(userForm);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        userForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
        userForm.setOutputMarkupId(true);
        userForm.add(new JSR303FormValidator());
        add(userForm);
    }
}
