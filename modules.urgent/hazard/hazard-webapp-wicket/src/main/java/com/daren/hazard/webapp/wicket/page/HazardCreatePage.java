package com.daren.majorhazardsource.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.majorhazardsource.api.biz.IHazardBeanService;
import com.daren.majorhazardsource.entities.HazardBean;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
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
    private IHazardBeanService majorHazardSourceService;

    Form<HazardBean> majorHazardSourceBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new HazardBean()));

    HazardBean hazardBean = new HazardBean();

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    public HazardCreatePage(final String id, final WebMarkupContainer wmc, final HazardBean bean) {
        super(id, wmc);
        if (null != bean) {
            hazardBean = bean;
        }
        initForm(hazardBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        majorHazardSourceBeanForm.setMultiPart(true);
        this.add(majorHazardSourceBeanForm);

        addTextFieldsToForm();

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("save", majorHazardSourceBeanForm) {
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
        add(ajaxSubmitLink);
        add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
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

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("enterpriseBeanId");
        addTextFieldToForm("expertName");
        addTextFieldToForm("lng");
        addTextFieldToForm("lat");
        addTextFieldToForm("accidentRate");
        addTextFieldToForm("estimate");
    }

}