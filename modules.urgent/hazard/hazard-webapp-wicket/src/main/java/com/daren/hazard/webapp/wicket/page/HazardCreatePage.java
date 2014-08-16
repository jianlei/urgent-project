package com.daren.hazard.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.hazard.entities.HazardBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
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

public class HazardCreatePage extends BasePanel {

    @Inject
    private IHazardBeanService hazardBeanService;

    Form<HazardBean> hazardBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new HazardBean()));

    HazardBean hazardBean = new HazardBean();

    final WebMarkupContainer dialogWrapper;
    IrisAbstractDialog dialog;
    Form<HazardBean> majorHazardSourceBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new HazardBean()));
    HazardBean majorHazardSourceBean = new HazardBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IHazardBeanService majorHazardSourceService;

    public HazardCreatePage(final String id, final WebMarkupContainer wmc, final HazardBean bean) {
        super(id, wmc);
        if (null != bean) {
            hazardBean = bean;
        }
        initForm(hazardBean);
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

        majorHazardSourceBeanForm.setMultiPart(true);
        this.add(majorHazardSourceBeanForm);

        addTextFieldsToForm();
        majorHazardSourceBeanForm.add(initGisButton());
        AjaxButton ajaxSubmitLink = new AjaxButton("save", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                HazardBean hazardBean = (HazardBean) majorHazardSourceBeanForm.getDefaultModelObject();
                if (null != hazardBean) {
                    try {
                        hazardBean.setUpdateDate(new Date());
                        majorHazardSourceService.saveEntity(hazardBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        majorHazardSourceBeanForm.add(ajaxSubmitLink);
        majorHazardSourceBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(HazardBean bean) {
        majorHazardSourceBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        majorHazardSourceBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        majorHazardSourceBeanForm.add(hiddenField);
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
        addTextFieldToForm("distanceOtherHazard");
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