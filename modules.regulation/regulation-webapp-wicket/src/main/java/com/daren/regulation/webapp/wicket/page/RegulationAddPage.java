package com.daren.regulation.webapp.wicket.page;

import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.regulation.api.biz.IRegulationBeanService;
import com.daren.regulation.entities.RegulationBean;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
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
public class RegulationAddPage extends Panel {
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    @Inject
    @Reference(id = "regulationBeanService", serviceInterface = IRegulationBeanService.class)
    private IRegulationBeanService regulationBeanService;
    private FeedbackPanel feedbackPanel; //信息显示

    public RegulationAddPage(String id, String type, IModel<RegulationBean> model) {
        super(id, model);
        this.type = type;

        if (model.getObject() == null)
        //new model
        {
            isAdd = true;
            initForm(Model.of(new RegulationBean()));
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

    private void initForm(IModel<RegulationBean> model) {
        final Form<RegulationBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));

        feedbackPanel = new FeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));

        dictForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("description").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    RegulationBean dictBean = (RegulationBean) form.getModelObject();
                    regulationBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new RegulationBean());
                    }
                    feedbackPanel.info(type + dictBean.getName() + "成功！");

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

        dictForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }

        });
        dictForm.setOutputMarkupId(true);
//        dictForm.add(new JSR303FormValidator());
        add(dictForm);
    }
}