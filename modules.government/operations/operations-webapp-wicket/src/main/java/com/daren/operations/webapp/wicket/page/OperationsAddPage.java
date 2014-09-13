package com.daren.operations.webapp.wicket.page;


import com.daren.core.web.wicket.BasePanel;
import com.daren.operations.api.biz.IOperationsService;
import com.daren.operations.entities.OperationsBean;
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

public class OperationsAddPage extends BasePanel {
    Form<OperationsBean> operationsBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new OperationsBean()));
    OperationsBean operationsBean = new OperationsBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IOperationsService operationsService;

    public OperationsAddPage(final String id, final WebMarkupContainer wmc, final OperationsBean bean) {
        super(id, wmc);
        if (null != bean) {
            operationsBean = bean;
        }
        initForm(operationsBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        operationsBeanForm.setMultiPart(true);
        this.add(operationsBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", operationsBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                OperationsBean operationsBean = (OperationsBean) form.getModelObject();
                if (null != operationsBean) {
                    try {
                        operationsService.saveEntity(operationsBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        operationsBeanForm.add(ajaxSubmitLink);
        operationsBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(OperationsBean bean) {
        operationsBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        operationsBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        operationsBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("enterpriseName");
        addTextFieldToForm("workType");
        addTextFieldToForm("operationProject");
    }
}