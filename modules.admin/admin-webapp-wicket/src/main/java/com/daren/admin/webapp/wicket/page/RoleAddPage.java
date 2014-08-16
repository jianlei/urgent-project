package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IRoleBeanService;
import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.entities.UserBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.model.util.ListModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    角色新增和修改页面类
 * 创建人:    sunlf
 * 创建时间:  2014/8/5 16:59
 * 修改人:    sunlf
 * 修改时间:  2014/8/5 16:59
 * 修改备注:  [说明本次修改内容]
 */
public class RoleAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private Palette<UserBean> palette;
    private boolean isAdd;
    @Inject
    @Reference(id = "roleBeanService", serviceInterface = IRoleBeanService.class)
    private IRoleBeanService roleBeanService;
    @Inject
    @Reference(id = "userBeanService", serviceInterface = IUserBeanService.class)
    private IUserBeanService userBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    //全部有效的用户
    private List<UserBean> availableUserList = new ArrayList<UserBean>();
    //选择的用户
    private List<UserBean> selectedUserList = new ArrayList<UserBean>();

    public RoleAddPage(String id, String type, IModel<RoleBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
        //new model
        {
            isAdd = true;
            initForm(Model.of(new RoleBean()));
        } else
        //edit model
        {
            isAdd = false;
            initForm(model);
        }


    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<RoleBean> model) {
        availableUserList = userBeanService.getAllUser();
        final Form<RoleBean> roleForm = new Form("roleForm", new CompoundPropertyModel(model));

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        roleForm.add(feedbackPanel.setOutputMarkupId(true));

        roleForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        roleForm.add(new TextField("remark").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
//      userForm.add(new TextField("loginDate").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        IChoiceRenderer<UserBean> renderer = new ChoiceRenderer<UserBean>("name", "id");
        selectedUserList = model.getObject().getUserList();
        //roleBeanService.getUserList(model.getObject());

        palette = new Palette<UserBean>("palette",
                new ListModel<UserBean>(selectedUserList),
                new CollectionModel<UserBean>(availableUserList),
                renderer, 10, false, true) {
            @Override
            protected Component newAvailableHeader(String componentId) {
                return new Label(componentId, "候选用户");
            }

            @Override
            protected Component newSelectedHeader(String componentId) {
                return new Label(componentId, "已选用户");
            }
        };

        roleForm.add(palette);

        roleForm.add(initSaveButton(roleForm));

        roleForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
        roleForm.setOutputMarkupId(true);
        roleForm.add(new JSR303FormValidator());
        add(roleForm);
    }

    private AjaxButton initSaveButton(final Form<RoleBean> roleForm) {
        return new AjaxButton("save", roleForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    RoleBean roleBean = (RoleBean) form.getModelObject();
                    roleBeanService.saveRoleUser(roleBean, (List) palette.getModelObject());
                    if (isAdd) {
                        roleForm.setModelObject(new RoleBean());
                        //palette.r
                    }
                    feedbackPanel.info(type + roleBean.getName() + "成功！");
                    target.add(roleForm);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    e.printStackTrace();
                    target.add(roleForm);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        };
    }
}