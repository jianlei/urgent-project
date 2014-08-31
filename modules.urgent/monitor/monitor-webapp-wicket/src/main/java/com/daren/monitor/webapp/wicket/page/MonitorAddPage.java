package com.daren.monitor.webapp.wicket.page;

import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.monitor.api.biz.IMonitorBeanService;
import com.daren.monitor.entities.MonitorBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Map;

//import com.daren.enterprise.webapp.component.form.EnterpriseSelect3Choice;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class MonitorAddPage extends BasePanel {

    MonitorBean monitorBean = new MonitorBean();
    Form<MonitorBean> monitorBeanForm = new Form("monitorForm", new CompoundPropertyModel(monitorBean));
    EnterpriseBean enterpriseBean = new EnterpriseBean();
    //    EnterpriseSelect3Choice<MonitorBean> listSites;
    IrisAbstractDialog dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IMonitorBeanService monitorBeanService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public MonitorAddPage(final String id, final WebMarkupContainer wmc, final MonitorBean bean) {
        super(id, wmc);
        if (null != bean) {
            monitorBean = bean;
            long enterpriseId = new Long(monitorBean.getAffiliation());
            enterpriseBean = (EnterpriseBean) enterpriseBeanService.getEntity(enterpriseId);
        }
        initForm(monitorBean);
        initFeedBack();
        addForm(id, wmc);
        addSelectToForm();
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        monitorBeanForm.setMultiPart(true);
        this.add(monitorBeanForm);

        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", monitorBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                MonitorBean monitorBean = (MonitorBean) monitorBeanForm.getDefaultModelObject();
                if (null != monitorBean) {
                    try {
                        //monitorBean.setUpdateDate(new Date());
                        monitorBeanService.saveEntity(monitorBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        monitorBeanForm.add(ajaxSubmitLink);
        monitorBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(MonitorBean bean) {
        monitorBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        monitorBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        monitorBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("type");
        addTextFieldToForm("ipAddress");
        addTextFieldToForm("port");
        addTextFieldToForm("channel");
        addTextFieldToForm("connectionProtocol");
        addTextFieldToForm("serial");
        addTextFieldToForm("admin");
        addTextFieldToForm("password");
    }

    //通过字典初始化下拉列表
    /*private void initSelect(String name) {
        //下拉列表
        listSites = new EnterpriseSelect3Choice<MonitorBean>(name,Model.of(monitorBean)){
            @Override
            public void setId(MonitorBean bean, String input) {
                bean.setAffiliation(input);
            }

            @Override
            public String getId(MonitorBean choice) {
                return choice.getAffiliation();
            }

            @Override
            public String getDisplayText(MonitorBean choice) {
                return enterpriseBean.getQymc();//企业名称
            }
        };
        monitorBeanForm.add(listSites);
    }*/

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        monitorBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        //initSelect("affiliation");
        initSelect("affiliation", enterpriseBeanService.getAllBeansToHashMap());
    }
}