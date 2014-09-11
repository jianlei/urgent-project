package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

/**
 * Created by Administrator on 2014/9/11.
 */
public class ChemistryManageAddPage extends BasePanel {
    Form<ChemistryManageBean> competencyBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new ChemistryManageBean()));
    ChemistryManageBean chemistryManageBean = new ChemistryManageBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IChemistryManageBeanService chemistryManageBeanService;

    public ChemistryManageAddPage(final String id, final WebMarkupContainer wmc, final ChemistryManageBean bean) {
        super(id, wmc);
        if (null != bean) {
            chemistryManageBean = bean;
        }
        initForm(chemistryManageBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        competencyBeanForm.setMultiPart(true);
        this.add(competencyBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", competencyBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ChemistryManageBean competencyBean = (ChemistryManageBean) form.getModelObject();
                if (null != competencyBean) {
                    try {
                        chemistryManageBeanService.saveEntity(competencyBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        competencyBeanForm.add(ajaxSubmitLink);
        competencyBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(ChemistryManageBean bean) {
        competencyBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        competencyBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        competencyBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("header");
        addTextFieldToForm("phone");
        addTextFieldToForm("address");
        addTextFieldToForm("mode");
        addTextFieldToForm("unitType");
        addTextFieldToForm("scope");
    }
}