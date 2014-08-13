package com.daren.expert.webapp.wicket.page;

import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.expert.api.biz.ISafetySupervisionExpertBeanService;
import com.daren.expert.entities.SafetySupervisionExpertBean;
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
 * Created by Administrator on 2014/8/6.
 */
public class SafetySupervisionExpertAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    @Inject
    @Reference(id = "safetySupervisionExpertBeanService", serviceInterface = ISafetySupervisionExpertBeanService.class)
    private ISafetySupervisionExpertBeanService safetySupervisionExpertBeanService;
    private FeedbackPanel feedbackPanel; //信息显示

    public SafetySupervisionExpertAddPage(String id, String type, IModel<SafetySupervisionExpertBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null) {
            isAdd = true;
            initForm(Model.of(new SafetySupervisionExpertBean()));
        } else {
            isAdd = false;
            initForm(model);
        }
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<SafetySupervisionExpertBean> model) {
        final Form<SafetySupervisionExpertBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));

        feedbackPanel = new FeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));

        dictForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("contactInformation").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("type").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    SafetySupervisionExpertBean dictBean = (SafetySupervisionExpertBean) form.getModelObject();
                    dictBean.setAttach(1l);
                    safetySupervisionExpertBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new SafetySupervisionExpertBean());
                    }
                    feedbackPanel.info(type + dictBean.getType() + "成功！");

                    target.add(form);
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

        dictForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
        dictForm.setOutputMarkupId(true);
//        dictForm.add(new JSR303FormValidator());
        add(dictForm);
    }
}