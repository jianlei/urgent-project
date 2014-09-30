package com.daren.monitor.webapp.wicket.page;

import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.daren.enterprise.webapp.component.form.EnterpriseSelect2Choice;
import com.daren.monitor.api.biz.ISingleMonitorBeanService;
import com.daren.monitor.entities.SingleMonitorBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

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

public class SingleMonitorAddPage extends BasePanel {

    SingleMonitorBean singleMonitorBean = new SingleMonitorBean();
    Form<SingleMonitorBean> singleMonitorBeanForm = new Form("singleMonitorForm", new CompoundPropertyModel(singleMonitorBean));
    EnterpriseBean enterpriseBean = new EnterpriseBean();
    //    EnterpriseSelect3Choice<SingleMonitorBean> listSites;
    EnterpriseSelect2Choice enterpriseSelect2Choice;
    IrisAbstractDialog dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private ISingleMonitorBeanService singleMonitorBeanService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public SingleMonitorAddPage(final String id, final WebMarkupContainer wmc, final SingleMonitorBean bean) {
        super(id, wmc);
        if (null != bean) {
            singleMonitorBean = bean;
            long enterpriseId = new Long(singleMonitorBean.getAffiliation());
            enterpriseBean = (EnterpriseBean) enterpriseBeanService.getEntity(enterpriseId);
        }
        initForm(singleMonitorBean);
        initFeedBack();
        addForm(id, wmc);
        enterpriseSelect2Choice = new EnterpriseSelect2Choice("affiliation", Model.of(enterpriseBean));
        enterpriseSelect2Choice.getSettings().setMinimumInputLength(2);
        singleMonitorBeanForm.add(enterpriseSelect2Choice);
        //addSelectToForm();
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        singleMonitorBeanForm.setMultiPart(true);
        this.add(singleMonitorBeanForm);

        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", singleMonitorBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                SingleMonitorBean singleMonitorBean = (SingleMonitorBean) singleMonitorBeanForm.getDefaultModelObject();
                if (null != singleMonitorBean) {
                    try {
                        //singleMonitorBean.setUpdateDate(new Date());
                        enterpriseBean = enterpriseSelect2Choice.getModelObject();
                        if (null != enterpriseBean) {
                            singleMonitorBean.setAffiliation(enterpriseBean.getQyid());
                        } else {
                            singleMonitorBean.setAffiliation("-1");
                        }

                        singleMonitorBeanService.saveEntity(singleMonitorBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        singleMonitorBeanForm.add(ajaxSubmitLink);
        singleMonitorBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(SingleMonitorBean bean) {
        singleMonitorBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        singleMonitorBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        singleMonitorBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("type");
        addTextFieldToForm("channel");
        addTextFieldToForm("connectionProtocol");
        addTextFieldToForm("serial");
        addTextFieldToForm("admin");
        addTextFieldToForm("password");
        addTextFieldToForm("equipmentNumber");
        addTextFieldToForm("equipmentId");
        addTextFieldToForm("streamingMediaIP");
        addTextFieldToForm("streamingMediaPort");
        addTextFieldToForm("pagIP");
        addTextFieldToForm("pagPort");
    }

    //通过字典初始化下拉列表
    /*private void initSelect(String name) {
        //下拉列表
        listSites = new EnterpriseSelect3Choice<SingleMonitorBean>(name,Model.of(singleMonitorBean)){
            @Override
            public void setId(SingleMonitorBean bean, String input) {
                bean.setAffiliation(input);
            }

            @Override
            public String getId(SingleMonitorBean choice) {
                return choice.getAffiliation();
            }

            @Override
            public String getDisplayText(SingleMonitorBean choice) {
                return enterpriseBean.getQymc();//企业名称
            }
        };
        singleMonitorBeanForm.add(listSites);
    }*/

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        singleMonitorBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        //initSelect("affiliation");
        initSelect("affiliation", enterpriseBeanService.getAllBeansToHashMap());
    }
}