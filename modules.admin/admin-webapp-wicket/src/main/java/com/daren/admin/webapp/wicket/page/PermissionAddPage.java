package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.entities.PermissionBean;
import com.daren.core.web.validation.JSR303FormValidator;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * @类描述：权限管理新增或修改类
 * @创建人：sunlf
 * @创建时间：2014-04-03 下午11:04
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class PermissionAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    private boolean isAddChild = false;
    private PermissionBean bean;
    //注入权限业务服务
    @Inject
    @Reference(id = "permissionBeanService", serviceInterface = IPermissionBeanService.class)
    private IPermissionBeanService permissionBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public PermissionAddPage(String id, String type, IModel<PermissionBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
        //new model
        {
            isAdd = true;
            initForm(Model.of(new PermissionBean()));
        } else
        //edit model
        {
            isAdd = false;
            initForm(model);
        }


    }

    /**
     * 创建子节点
     *
     * @param id
     * @param newTabType
     * @param model
     * @param bean
     */
    public PermissionAddPage(String id, String newTabType, IModel<PermissionBean> model, PermissionBean bean) {
        super(id, model);
        this.type = newTabType;
        this.bean = bean;
        PermissionBean permissionBean = new PermissionBean();
        permissionBean.setParent(bean);
        initForm(Model.of(permissionBean));
        isAddChild = true;
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<PermissionBean> model) {
        final Form<PermissionBean> permissionForm = new Form("permissionForm", new CompoundPropertyModel(model));

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        permissionForm.add(feedbackPanel.setOutputMarkupId(true));

        permissionForm.add(new Label("parent.name"));
        permissionForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        permissionForm.add(new TextField("sort").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        permissionForm.add(new TextField("permission"));
        permissionForm.add(new TextField("isShow").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        permissionForm.add(new AjaxButton("save", permissionForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    PermissionBean permissionBean = (PermissionBean) form.getModelObject();
                    permissionBeanService.saveEntity(permissionBean);
                    if (isAdd) {
                        permissionForm.setModelObject(new PermissionBean());
                    }
                    if (isAddChild) {
                        PermissionBean bean1 = new PermissionBean();
                        bean1.setParent(bean);
                        permissionForm.setModelObject(bean1);
                    }
                    feedbackPanel.info(type + permissionBean.getName() + "成功！");
                    target.add(permissionForm);
                } catch (Exception e) {
                    feedbackPanel.error(type + "失败！");
                    target.add(permissionForm);
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        permissionForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
        permissionForm.setOutputMarkupId(true);
        permissionForm.add(new JSR303FormValidator());
        add(permissionForm);
    }
}
