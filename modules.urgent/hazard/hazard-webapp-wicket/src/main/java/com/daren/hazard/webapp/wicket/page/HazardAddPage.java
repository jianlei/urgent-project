package com.daren.hazard.webapp.wicket.page;

import com.daren.core.web.component.map.WindowMapPage;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.form.EnterpriseSelect2Choice;
import com.daren.hazard.api.biz.IHazardBeanService;
import com.daren.hazard.entities.HazardBean;
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
import org.apache.wicket.model.Model;

import javax.inject.Inject;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class HazardAddPage extends BasePanel {

    final WebMarkupContainer dialogWrapper;
    Form<HazardBean> hazardBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new HazardBean()));

    EnterpriseBean enterpriseBean=new EnterpriseBean();
    HazardBean hazardBean = new HazardBean();
    EnterpriseSelect2Choice enterpriseSelect2Choice;
    WindowMapPage dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IHazardBeanService hazardBeanService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public HazardAddPage(final String id, final WebMarkupContainer wmc, final HazardBean bean) {
        super(id, wmc);
        if (null != bean) {
            hazardBean = bean;
            enterpriseBean=enterpriseBeanService.getByQyid(hazardBean.getQyid());
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

        hazardBeanForm.setMultiPart(true);
        this.add(hazardBeanForm);

        enterpriseSelect2Choice = new EnterpriseSelect2Choice("qyid", Model.of(enterpriseBean));
        hazardBeanForm.add(enterpriseSelect2Choice);
        addTextFieldsToForm();
        hazardBeanForm.add(initGisButton());
        AjaxButton ajaxSubmitLink = new AjaxButton("save", hazardBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                HazardBean hazardBean = (HazardBean) hazardBeanForm.getDefaultModelObject();
                if (null != hazardBean) {
                    try {
                        EnterpriseBean enterpriseBean=enterpriseSelect2Choice.getModelObject();
                        hazardBean.setQyid(enterpriseBean.getQyid());
                        hazardBeanService.saveEntity(hazardBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        hazardBeanForm.add(ajaxSubmitLink);
        hazardBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(HazardBean bean) {
        hazardBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        hazardBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        hazardBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addHiddenFieldToForm("jd");
        addHiddenFieldToForm("wd");
        addTextFieldToForm("estimate");
        addTextFieldToForm("name");
        addTextFieldToForm("expertName");
        addTextFieldToForm("accidentRate");

//        addTextFieldToForm("enterpriseBeanId");
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
        dialog = new WindowMapPage("dialog", title) {
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