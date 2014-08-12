package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentViewPage extends BasePanel {

    @Inject
    private IAccidentBeanService accidentBeanService;

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    Form<AccidentBean> accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(new AccidentBean()));
    Form<EnterpriseBean> enterpriseForm = new Form("enterpriseForm", new CompoundPropertyModel(new EnterpriseBean()));

    public AccidentViewPage(final String id, final WebMarkupContainer wmc, AccidentBean accidentBean, AccidentPage accidentPage) {
        super(id, wmc);
        if (null == accidentBean) {
            accidentBean = new AccidentBean();
        }

        initForm(accidentBean.getId());
        addForm(id, wmc, accidentBean, accidentPage);
    }

    public void addForm(final String id, final WebMarkupContainer wmc, final AccidentBean accidentBean, final AccidentPage accidentPage) {
        accidentBeanForm.setMultiPart(true);
        this.add(accidentBeanForm);
        accidentBeanForm.setModelObject(accidentBean);
        addLabelToForm(accidentBean);
        addEnterpriseForm(accidentBean);

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("edit", accidentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean accidentBean = (AccidentBean) form.getDefaultModelObject();
                if (null != accidentBean) {
                    accidentPage.goAccidentEditPage(wmc, accidentBean, target);
                }
            }
        };
        add(ajaxSubmitLink);
    }

    private void addEnterpriseForm(final AccidentBean accidentBean) {
        EnterpriseBean enterpriseBean = new EnterpriseBean();
        if (null != accidentBean && !"".equals(accidentBean.getAccidentUnit())) {
            enterpriseBean = (EnterpriseBean) enterpriseBeanService.getEntity(Long.parseLong(accidentBean.getAccidentUnit()));
        }
        if(enterpriseBean == null){
            enterpriseBean = new EnterpriseBean();
        }
        enterpriseForm.setMultiPart(true);
        this.add(enterpriseForm);
        enterpriseForm.setModelObject(enterpriseBean);
        addLabelToForm(enterpriseBean);
    }

    private void initForm(long accidentBeanId) {
        if (-1 != accidentBeanId) {
            AccidentBean accidentBean = (AccidentBean) accidentBeanService.getEntity(accidentBeanId);
            accidentBeanForm.setModelObject(accidentBean);
        }
    }

    private void addLabelToForm(String name, String value, Form form) {
        Label label = new Label(name, value);
        form.add(label);
    }

    private void addLabelToForm(EnterpriseBean enterpriseBean) {
        addLabelToForm("qymc", enterpriseBean.getQymc(), enterpriseForm);
        addLabelToForm("address_jy", enterpriseBean.getAddress_jy(), enterpriseForm);
        addLabelToForm("zyjyxm", enterpriseBean.getZyjyxm(), enterpriseForm);
        addLabelToForm("qygmbm", enterpriseBean.getQygmbm(), enterpriseForm);
    }
    private void addLabelToForm(AccidentBean accidentBean) {

        addLabelToForm("signer", accidentBean.getSigner(), accidentBeanForm);
        addLabelToForm("liaisonsPhone", accidentBean.getLiaisonsPhone(), accidentBeanForm);
        addLabelToForm("liaisons", accidentBean.getLiaisons(), accidentBeanForm);
        addLabelToForm("operatorPhone", accidentBean.getOperatorPhone(), accidentBeanForm);
        addLabelToForm("operator", accidentBean.getOperator(), accidentBeanForm);
        addLabelToForm("videoLink", accidentBean.getVideoLink(), accidentBeanForm);
        addLabelToForm("attachment", accidentBean.getAttachment(), accidentBeanForm);
        addLabelToForm("measure", accidentBean.getMeasure(), accidentBeanForm);
        addLabelToForm("describe", accidentBean.getAccidentDescribe(), accidentBeanForm);
        addLabelToForm("headcountEvacuees", accidentBean.getHeadcountEvacuees(), accidentBeanForm);
        addLabelToForm("headcountTrappedOrMissing", accidentBean.getHeadcountTrappedOrMissing(), accidentBeanForm);
        addLabelToForm("headcountSlight", accidentBean.getHeadcountSlight(), accidentBeanForm);
        addLabelToForm("headcountSerious", accidentBean.getHeadcountSerious(), accidentBeanForm);
        addLabelToForm("headcountDeath", accidentBean.getHeadcountDeath(), accidentBeanForm);
        addLabelToForm("accidentScene", accidentBean.getAccidentScene(), accidentBeanForm);
        addLabelToForm("economicLosses", accidentBean.getEconomicLosses(), accidentBeanForm);
        addLabelToForm("accidentPreliminaryAnalysis", accidentBean.getAccidentPreliminaryAnalysis(), accidentBeanForm);
        addLabelToForm("detailsPlace", accidentBean.getDetailsPlace(), accidentBeanForm);
        addLabelToForm("place", accidentBean.getPlace(), accidentBeanForm);
        addLabelToForm("industryCategory", accidentBean.getIndustryCategory(), accidentBeanForm);
        addLabelToForm("accidentLevel", accidentBean.getAccidentLevel(), accidentBeanForm);
        addLabelToForm("accidentType", accidentBean.getAccidentType(), accidentBeanForm);
        addLabelToForm("accidentTime", accidentBean.getAccidentTime(), accidentBeanForm);
        addLabelToForm("accidentTitle", accidentBean.getAccidentTitle(), accidentBeanForm);
    }
}
