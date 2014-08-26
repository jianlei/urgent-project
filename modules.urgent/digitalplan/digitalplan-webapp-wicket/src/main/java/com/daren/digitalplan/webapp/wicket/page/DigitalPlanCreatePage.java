package com.daren.digitalplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.entities.DigitalPlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Date;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DigitalPlanCreatePage extends BasePanel {

    @Inject
    private IDigitalPlanBeanService digitalplanBeanService;

    Form<DigitalPlanBean> digitalplanBeanForm = new Form("majorDigitalPlanSourceForm", new CompoundPropertyModel(new DigitalPlanBean()));

    DigitalPlanBean digitalplanBean = new DigitalPlanBean();

    final WebMarkupContainer dialogWrapper;
    IrisAbstractDialog dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");


    public DigitalPlanCreatePage(final String id, final WebMarkupContainer wmc, final DigitalPlanBean bean) {
        super(id, wmc);
        if (null != bean) {
            digitalplanBean = bean;
        }
        initForm(digitalplanBean);
        initFeedBack();
        addForm(id, wmc);
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog"));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        digitalplanBeanForm.setMultiPart(true);
        this.add(digitalplanBeanForm);

        addTextFieldsToForm();
        digitalplanBeanForm.add(initGisButton());
        AjaxButton ajaxSubmitLink = new AjaxButton("save", digitalplanBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DigitalPlanBean digitalplanBean = (DigitalPlanBean) digitalplanBeanForm.getDefaultModelObject();
                if (null != digitalplanBean) {
                    try {
                        digitalplanBean.setUpdateDate(new Date());
                        digitalplanBeanService.saveEntity(digitalplanBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        digitalplanBeanForm.add(ajaxSubmitLink);
        digitalplanBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(DigitalPlanBean bean) {
        digitalplanBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        digitalplanBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        digitalplanBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addHiddenFieldToForm("lng");
        addHiddenFieldToForm("lat");
        addTextFieldToForm("estimate");
        addTextFieldToForm("name");
        addTextFieldToForm("expertName");
        addTextFieldToForm("accidentRate");
        addTextFieldToForm("enterpriseBeanId");
        addTextFieldToForm("place");
        addTextFieldToForm("startTime");
        addTextFieldToForm("level");
        addTextFieldToForm("describe");
        addTextFieldToForm("inChemical");
        addTextFieldToForm("distanceOtherDigitalPlan");
        addTextFieldToForm("scope500MHaveMans");
        addTextFieldToForm("lastThreeYearAccident");
        addTextFieldToForm("roadConditions");
        addTextFieldToForm("zbqkd");
        addTextFieldToForm("sfwhqyd");
        addTextFieldToForm("jbqyzjzxjld");
        addTextFieldToForm("zbqklxd");
        addTextFieldToForm("zbqkn");
        addTextFieldToForm("sfwhqydn");
        addTextFieldToForm("jbqyzjzxjln");
        addTextFieldToForm("zbqklxn");
        addTextFieldToForm("zbqkx");
        addTextFieldToForm("sfwhqyx");
        addTextFieldToForm("jbqyzjzxjlx");
        addTextFieldToForm("zbqklxx");
        addTextFieldToForm("zbqkb");
        addTextFieldToForm("sfwhqyb");
        addTextFieldToForm("jbqyzjzxjlb");
        addTextFieldToForm("zbqklxb");
        addTextFieldToForm("yxfw");
        addTextFieldToForm("jbqk");
        addTextFieldToForm("zlcs");
        addTextFieldToForm("remark");
    }

    private void createDialog(AjaxRequestTarget target, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new MapPage("dialog", title, null) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {

            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    private AjaxLink initGisButton() {
        AjaxLink alink = new AjaxLink("gisSrc") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "标注地址");
            }
        };
        return alink;
    }

}