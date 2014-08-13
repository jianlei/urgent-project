package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentViewPage extends BasePanel {

    Form<AccidentBean> accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(new AccidentBean()));
    @Inject
    private IAccidentBeanService accidentBeanService;

    public AccidentViewPage(final String id, final WebMarkupContainer wmc, AccidentBean accidentBean) {
        super(id, wmc);
        if (null == accidentBean) {
            accidentBean = new AccidentBean();
        }
        initForm(accidentBean.getId());
        addForm(id, wmc, accidentBean);
    }

    public void addForm(final String id, final WebMarkupContainer wmc, final AccidentBean accidentBean) {
        accidentBeanForm.setMultiPart(true);
        this.add(accidentBeanForm);

        addTextFieldsToForm(accidentBean);

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("edit", accidentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean accidentBean = (AccidentBean) form.getDefaultModelObject();
                if (null != accidentBean) {
                    try {
                        target.appendJavaScript("alert('保存成功')");
                    } catch (Exception e) {
                        target.appendJavaScript("alert('保存失败')");
                    }
                }
            }
        };
        add(ajaxSubmitLink);
    }

    private void initForm(long accidentBeanId) {
        if (-1 != accidentBeanId) {
            AccidentBean accidentBean = (AccidentBean) accidentBeanService.getEntity(accidentBeanId);
            accidentBeanForm.setModelObject(accidentBean);
        }
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        accidentBeanForm.add(textField);
    }

    private void addLabelToForm(String name, String value) {
        Label label = new Label(name, value);
        accidentBeanForm.add(label);
    }

    private void addLabelToForm(String name, Date value) {
        Label label = new Label(name, value);
        accidentBeanForm.add(label);
    }

    private void addTextFieldsToForm(AccidentBean accidentBean) {
        addLabelToForm("signer", accidentBean.getSigner());
        addLabelToForm("liaisonsPhone", accidentBean.getLiaisonsPhone());
        addLabelToForm("liaisons", accidentBean.getLiaisons());
        addLabelToForm("operatorPhone", accidentBean.getOperatorPhone());
        addLabelToForm("operator", accidentBean.getOperator());
        addLabelToForm("videoLink", accidentBean.getVideoLink());
        addLabelToForm("attachment", accidentBean.getAttachment());
        addLabelToForm("measure", accidentBean.getMeasure());
        addLabelToForm("describe", accidentBean.getAccidentDescribe());
        addLabelToForm("headcountEvacuees", accidentBean.getHeadcountEvacuees());
        addLabelToForm("headcountTrappedOrMissing", accidentBean.getHeadcountTrappedOrMissing());
        addLabelToForm("headcountSlight", accidentBean.getHeadcountSlight());
        addLabelToForm("headcountSerious", accidentBean.getHeadcountSerious());
        addLabelToForm("headcountDeath", accidentBean.getHeadcountDeath());
        addLabelToForm("accidentScene", accidentBean.getAccidentScene());
        addLabelToForm("economicLosses", accidentBean.getEconomicLosses());
        addLabelToForm("accidentPreliminaryAnalysis", accidentBean.getAccidentPreliminaryAnalysis());
        addLabelToForm("detailsPlace", accidentBean.getDetailsPlace());
        addLabelToForm("place", accidentBean.getPlace());
        addLabelToForm("industryCategory", accidentBean.getIndustryCategory());
        addLabelToForm("accidentLevel", accidentBean.getAccidentLevel());
        addLabelToForm("accidentType", accidentBean.getAccidentType());
        addLabelToForm("accidentTime", accidentBean.getAccidentTime());
    }
}
