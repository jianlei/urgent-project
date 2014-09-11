package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * Created by Administrator on 2014/9/11.
 */
public class ChemistryManageAddPage extends Panel{
    @Inject
    @Reference(id = "chemistryManageBeanService", serviceInterface = IChemistryManageBeanService.class)
    protected IChemistryManageBeanService chemistryManageBeanService;
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    private boolean isAdd;
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    public ChemistryManageAddPage(String id, IModel<ChemistryManageBean> model, String type) {
        super(id, model);
        this.type = type;
        if (model.getObject() == null) {
            isAdd = true;
            initForm(Model.of(new ChemistryManageBean()));
        } else {
            isAdd = false;
            initForm(model);
        }
    }

    private void initForm(IModel<ChemistryManageBean> model) {
        final Form<ChemistryManageBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));
        dictForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("header").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("phone").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("address").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("mode").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("unitType").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("scope").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    ChemistryManageBean dictBean = (ChemistryManageBean) form.getModelObject();
                    chemistryManageBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new ChemistryManageBean());
                    }
                    feedbackPanel.info("保存成功！");
                    target.add(form);
                } catch (Exception e) {
                    feedbackPanel.error("保存失败！");
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
        add(dictForm);
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }
}
