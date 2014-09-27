package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.admin.api.biz.IUserBeanService;
import com.daren.admin.entities.UserBean;
import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
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
 * Created by Administrator on 2014/9/11.
 */
public class ChemistryManageAddPage extends BasePanel {
   ChemistryManageBean chemistryManageBean = new ChemistryManageBean();
    Form<ChemistryManageBean> competencyBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(chemistryManageBean));

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IChemistryManageBeanService chemistryManageBeanService;
    @Inject
    private IUserBeanService userBeanService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public ChemistryManageAddPage(final String id, final WebMarkupContainer wmc, final ChemistryManageBean bean) {
        super(id, wmc);
        if (null != bean) {
            chemistryManageBean = bean;
        }
        UserBean userBean = userBeanService.getCurrentUser();
        EnterpriseBean enterpriseBean = enterpriseBeanService.getByQyid(userBean.getQyid());
        chemistryManageBean.setQyCode(enterpriseBean.getQyid());
        chemistryManageBean.setQyName(enterpriseBean.getQymc());
        chemistryManageBean.setAddress(enterpriseBean.getAddresszc());
        chemistryManageBean.setPhone(userBean.getMobile());
        initForm(chemistryManageBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        competencyBeanForm.setMultiPart(true);
        this.add(competencyBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", competencyBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ChemistryManageBean competencyBean = (ChemistryManageBean) form.getModelObject();
                if (null != competencyBean) {
                    try {
                        competencyBean.setLoginName(userBeanService.getCurrentUserLoginName());
                        chemistryManageBeanService.saveEntity(competencyBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        competencyBeanForm.add(ajaxSubmitLink);
        competencyBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(ChemistryManageBean bean) {
        competencyBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        competencyBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        competencyBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("qyCode");
        addTextFieldToForm("qyName");
        addTextFieldToForm("address");
        addTextFieldToForm("phone");
        addTextFieldToForm("fax");
        addTextFieldToForm("zipCode");
        addTextFieldToForm("qyType");
        addTextFieldToForm("illegalPerson");
        addTextFieldToForm("specialType");
        addTextFieldToForm("economicsNature");
        addTextFieldToForm("directorUnits");
        addTextFieldToForm("registrationAuthority");
        addTextFieldToForm("mainHead");
        addTextFieldToForm("mainHeadId");
        addTextFieldToForm("chargeHead");
        addTextFieldToForm("chargeHeadId");
        addTextFieldToForm("workersNumber");
        addTextFieldToForm("technologyNumber");
        addTextFieldToForm("safetyNumber");
        addTextFieldToForm("registrationCapital");
        addTextFieldToForm("fixedAssets");
        addTextFieldToForm("yearSale");
        addTextFieldToForm("manageAddress");
        addTextFieldToForm("manageProperty");
        addTextFieldToForm("storageAddress");
        addTextFieldToForm("storageProperty");
        addTextFieldToForm("buildingStructure");
        addTextFieldToForm("storageCapacity");
        addTextFieldToForm("systemName");
        addTextFieldToForm("communication");
        addTextFieldToForm("mode");
        addTextFieldToForm("unitType");
        addTextFieldToForm("scope");
    }
}