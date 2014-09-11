package com.daren.competency.webapp.wicket.page;


import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.entities.CompetencyBean;
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
 * @类描述：培训
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetencyAddPage extends BasePanel {
    Form<CompetencyBean> competencyBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new CompetencyBean()));
    CompetencyBean competencyBean = new CompetencyBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private ICompetencyService competencyService;

    public CompetencyAddPage(final String id, final WebMarkupContainer wmc, final CompetencyBean bean) {
        super(id, wmc);
        if (null != bean) {
            competencyBean = bean;
        }
        initForm(competencyBean);
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
                CompetencyBean competencyBean = (CompetencyBean) form.getModelObject();
                if (null != competencyBean) {
                    try {
                        competencyService.saveEntity(competencyBean);
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

    private void initForm(CompetencyBean bean) {
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
        addTextFieldToForm("sex");
        addTextFieldToForm("enterpriseId");
        addTextFieldToForm("title");
        addTextFieldToForm("cultureLevel");
        addTextFieldToForm("id_code");
        addTextFieldToForm("unitType");
        addTextFieldToForm("qualificationsType");
        addTextFieldToForm("awardDate");
        addTextFieldToForm("effectiveDate");
        addTextFieldToForm("code");
    }
}