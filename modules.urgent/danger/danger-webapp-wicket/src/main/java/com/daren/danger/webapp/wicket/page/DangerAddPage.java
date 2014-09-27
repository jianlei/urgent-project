package com.daren.danger.webapp.wicket.page;

import com.daren.core.web.component.map.WindowMapPage;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.form.EnterpriseSelect2Choice;
import com.daren.danger.api.biz.IDangerBeanService;
import com.daren.danger.entities.DangerBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;


/**
 * @类描述：危化品添加
 * @创建人：xukexin
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DangerAddPage extends BasePanel {

    final WebMarkupContainer dialogWrapper;
    Form<DangerBean> dangerBeanForm = new Form("majorDangerSourceForm", new CompoundPropertyModel(new DangerBean()));

    EnterpriseBean enterpriseBean=new EnterpriseBean();
    DangerBean dangerBean = new DangerBean();
    EnterpriseSelect2Choice enterpriseSelect2Choice;
    WindowMapPage dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IDangerBeanService dangerBeanService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public DangerAddPage(final String id, final WebMarkupContainer wmc, final DangerBean bean) {
        super(id, wmc);
        if (null != bean) {
            dangerBean = bean;
            //enterpriseBean=enterpriseBeanService.getByQyid(dangerBean.getQyid());
        }
        initForm(dangerBean);
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

        dangerBeanForm.setMultiPart(true);
        this.add(dangerBeanForm);

        final TextArea<String> physical_property = new TextArea<String>("physical_property");
        dangerBeanForm.add(physical_property);
        final TextArea<String> harm_health = new TextArea<String>("harm_health");
        dangerBeanForm.add(harm_health);
        final TextArea<String> first_aid = new TextArea<String>("first_aid");
        dangerBeanForm.add(first_aid);
        final TextArea<String> fire_control = new TextArea<String>("fire_control");
        dangerBeanForm.add(fire_control);
        final TextArea<String> release_measures = new TextArea<String>("release_measures");
        dangerBeanForm.add(release_measures);
        final TextArea<String> control_setting = new TextArea<String>("control_setting");
        dangerBeanForm.add(control_setting);
        final TextArea<String> touch_control = new TextArea<String>("touch_control");
        dangerBeanForm.add(touch_control);
        final TextArea<String> stability = new TextArea<String>("stability");
        dangerBeanForm.add(stability);
        final TextArea<String> toxicology_data = new TextArea<String>("toxicology_data");
        dangerBeanForm.add(toxicology_data);
        final TextArea<String> transport_information = new TextArea<String>("transport_information");
        dangerBeanForm.add(transport_information);


        /*enterpriseSelect2Choice = new EnterpriseSelect2Choice("qyid", Model.of(enterpriseBean));
        dangerBeanForm.add(enterpriseSelect2Choice);*/
        addTextFieldsToForm();
        //dangerBeanForm.add(initGisButton());
        AjaxButton ajaxSubmitLink = new AjaxButton("save", dangerBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DangerBean dangerBean = (DangerBean) dangerBeanForm.getDefaultModelObject();
                if (null != dangerBean) {
                    try {
                        /*EnterpriseBean enterpriseBean=enterpriseSelect2Choice.getModelObject();
                        dangerBean.setQyid(enterpriseBean.getQyid());*/
                        dangerBeanService.saveEntity(dangerBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        dangerBeanForm.add(ajaxSubmitLink);
        dangerBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(DangerBean bean) {
        dangerBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        dangerBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        dangerBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("chinese_name");
        addTextFieldToForm("chinese_otherName");
        addTextFieldToForm("english_name");
        addTextFieldToForm("english_otherName");
        addTextFieldToForm("CAS_num");
        addTextFieldToForm("formula");
        addTextFieldToForm("formula_weight");
        //addTextFieldToForm("physical_property");
        addTextFieldToForm("shape");
        addTextFieldToForm("melting_point");
        addTextFieldToForm("tolerance");
        addTextFieldToForm("boiling_point");
        addTextFieldToForm("critical_pressure");
        addTextFieldToForm("density_ratio");
        addTextFieldToForm("burn_Hot");
        addTextFieldToForm("flash_point");
        addTextFieldToForm("nature_temperature");
        addTextFieldToForm("explode_upper");
        addTextFieldToForm("explode_below");
        //addTextFieldToForm("harm_health");
        //addTextFieldToForm("first_aid");
        //addTextFieldToForm("fire_control");
        //addTextFieldToForm("release_measures");
        //addTextFieldToForm("control_setting");
        //addTextFieldToForm("touch_control");
        //addTextFieldToForm("stability");
        //addTextFieldToForm("toxicology_data");
        //addTextFieldToForm("transport_information");
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

    /*private AjaxLink initGisButton() {
        AjaxLink alink = new AjaxLink("gisSrc") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "标注地址");
            }
        };
        return alink;
    }*/

}