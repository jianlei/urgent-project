package com.daren.competency.webapp.wicket.page;


import com.daren.competency.api.biz.ICompetencyService;
import com.daren.competency.entities.CompetencyBean;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;


/**
 * @类描述：培训
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CompetencyAddPage extends BasePanel {
    CompetencyBean competencyBean = new CompetencyBean();
    Form<CompetencyBean> competencyBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(competencyBean));
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
        addDatePickerToForm(competencyBean, competencyBeanForm);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        competencyBeanForm.setMultiPart(true);
        this.add(competencyBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", competencyBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    competencyService.saveEntity(competencyBean);
                    feedbackPanel.info("保存成功！");
                    target.add(feedbackPanel);
                } catch (Exception e) {
                    feedbackPanel.info("保存失败！");
                    target.add(feedbackPanel);
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

    //日期控件//
    private void addDatePickerToForm(String value, String dateFormat, String formatOne, String formatTwo, CompetencyBean bean, Form form) {
        DatePicker accidentTime = new DatePicker(value,
                new PropertyModel<Date>(bean, value), formatOne,
                new Options(dateFormat, Options.asString(formatTwo)));
        form.add(accidentTime);
    }

    private void addDatePickerToForm(CompetencyBean bean, Form form) {
        addDatePickerToForm("awardDate", "dateFormat", "yyyy-MM-dd", "yy-mm-dd", bean, form);
        addDatePickerToForm("effectiveDate", "dateFormat", "yyyy-MM-dd", "yy-mm-dd", bean, form);
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
        addTextFieldToForm("code");
    }
}