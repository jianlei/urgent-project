package com.daren.enterprise.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IOrganizationBeanService;
import com.daren.enterprise.entities.OrganizationBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * @类描述：新建监管机构
 * @创建人：xukexin
 * @创建时间：9:24
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OrganizationAddPage extends BasePanel {
    Form<OrganizationBean> organizationBeanForm = new Form("organizationForm", new CompoundPropertyModel(new OrganizationBean()));
    OrganizationBean organizationBean = new OrganizationBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IOrganizationBeanService organizationBeanService;

    public  OrganizationAddPage(final String id, final WebMarkupContainer wmc, OrganizationBean bean) {
        super(id, wmc);
        if (null != bean) {
            organizationBean = bean;
        }
        initForm(organizationBean);
        initFeedBack();
        addForm(id, wmc);
        addSelectToForm();
    }

    private void addForm(final String id, final WebMarkupContainer wmc) {

        organizationBeanForm.setMultiPart(true);
        this.add(organizationBeanForm);

        addTextFieldsToForm();

        //日期控件//
        final DatePicker datePicker = new DatePicker("createtime",
                new PropertyModel<Date>(organizationBean, "createtime"), "yyyy-mm-dd",
                new Options("dateFormat", Options.asString("yyyy-mm-dd")));
        organizationBeanForm.add(datePicker);
        /**
         * 保存
         */
        AjaxButton ajaxButton = new AjaxButton("save", organizationBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                OrganizationBean organizatioBean = ( OrganizationBean) organizationBeanForm.getDefaultModelObject();
                if (null != organizatioBean) {
                    try {
                        organizatioBean.setUpdateDate(new Date());
                        organizationBeanService.saveEntity(organizatioBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
            //验证字段
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        };
        organizationBeanForm.add(ajaxButton);
        organizationBeanForm.add(new IrisIndicatingAjaxLink("cancel") {
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

    private void initForm( OrganizationBean bean) {
        organizationBeanForm.setModelObject(organizationBean);
    }

    /**
     * 下拉选择初始化
     */
    private void addSelectToForm() {
        initSelect("zfbj", IDictConstService.ORGANIZATION_ZFBJ);
        initSelect("jgbmbj", IDictConstService.ORGANIZATION_JGBMBJ);
        initSelect("jglxbj", IDictConstService.ORGANIZATION_JGLXBJ);
    }
    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        organizationBeanForm.add(listSites);
    }
    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        organizationBeanForm.add(listSites);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        organizationBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("jgdm");
        addTextFieldToForm("sjjgdm");
        addTextFieldToForm("mc");
        addTextFieldToForm("mcj");
        addTextFieldToForm("xzqh_dm");
    }
}
