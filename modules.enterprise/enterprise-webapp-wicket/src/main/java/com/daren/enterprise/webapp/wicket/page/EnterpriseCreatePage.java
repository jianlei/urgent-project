package com.daren.enterprise.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.converter.DateConverter;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseCreatePage extends BasePanel {

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;
    Form<EnterpriseBean> enterpriseBeanForm = new Form("enterpriseForm", new CompoundPropertyModel(new EnterpriseBean()));

    EnterpriseBean enterpriseBean = new EnterpriseBean();

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    public EnterpriseCreatePage(final String id, final WebMarkupContainer wmc, EnterpriseBean bean) {
        super(id, wmc);
        if (null != bean) {
            enterpriseBean = bean;
        }
        initForm(enterpriseBean);
        initFeedBack();
        addForm(id, wmc);
    }

    private void addForm(final String id, final WebMarkupContainer wmc) {

        enterpriseBeanForm.setMultiPart(true);
        this.add(enterpriseBeanForm);

        addTextFieldsToForm();

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("save", enterpriseBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EnterpriseBean enterpriseBean = (EnterpriseBean) enterpriseBeanForm.getDefaultModelObject();
                if (null != enterpriseBean) {
                    try {
                        enterpriseBean.setUpdateDate(new Date());
                        enterpriseBeanService.saveEntity(enterpriseBean);
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

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initForm(EnterpriseBean bean) {
        enterpriseBeanForm.setModelObject(enterpriseBean);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        enterpriseBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("qymc");
        addTextFieldToForm("frdb");
        addTextFieldToForm("cl_sj");
        addTextFieldToForm("address_zc");
        addTextFieldToForm("address_jy");
        addTextFieldToForm("postcode");
        addTextFieldToForm("qylxfs");
        addTextFieldToForm("mailaddress");
        addTextFieldToForm("zyjyxm");
        addTextFieldToForm("zyyl");
        addTextFieldToForm("zycp");
        addTextFieldToForm("jgjgdm");
        addTextFieldToForm("zzjgdm");
        addTextFieldToForm("sdlx");
        addTextFieldToForm("jjlxbm");
        addTextFieldToForm("cyrs");
        addTextFieldToForm("zcaqgcsrs");
        addTextFieldToForm("aqrs");
        addTextFieldToForm("jzaqrs");
        addTextFieldToForm("hyml_dm");
        addTextFieldToForm("hylbbm");
        addTextFieldToForm("xjqybj");
        addTextFieldToForm("sjqyid");
        addTextFieldToForm("hyzxl_dm");
        addTextFieldToForm("qygmbm");
        addTextFieldToForm("gmqk");
        addTextFieldToForm("zczc");
        addTextFieldToForm("jgfl");
        addTextFieldToForm("sndxssr");
        addTextFieldToForm("aqscfy");
        addTextFieldToForm("yazlqy_bj");
        addTextFieldToForm("zdxfdw_bj");
        addTextFieldToForm("zybbj");
        addTextFieldToForm("aqjgszqk");
        addTextFieldToForm("dlwz");
    }

}