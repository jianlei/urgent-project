package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentEditPage extends BasePanel {

    @Inject
    private IAccidentBeanService accidentBeanService;

    Form<AccidentBean> accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(new AccidentBean()));

    final private String selectedAccidentType = "触电";
    final private String selectedAccidentLevel = "触电";
    final private String selectedIndustryCategory = "触电";
    final private String selectedAccidentPreliminaryAnalysis = "触电";

    public AccidentEditPage(final String id, final WebMarkupContainer wmc, AccidentBean accidentBean) {
        super(id, wmc);
        if(null == accidentBean){
            accidentBean = new AccidentBean();
        }
        initForm(accidentBean.getId());
        addForm(id, wmc,accidentBean);

    }

    public void addForm(final String id, final WebMarkupContainer wmc,final AccidentBean accidentBean) {
        accidentBeanForm.setMultiPart(true);
        accidentBeanForm.setModelObject(accidentBean);
        this.add(accidentBeanForm);
        addTextFieldsToForm();
        addSelectToForm();

        //多行文本

        final TextArea<String> accidentDescribe = new TextArea<String>("accidentDescribe", Model.of(""));
        accidentBeanForm.add(accidentDescribe);

        //日期控件//
//        final DatePicker accidentTime = new DatePicker("accidentTime", Model.of(new Date()));
//        accidentBeanForm.add(accidentTime);

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("save", accidentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean accidentBean = (AccidentBean) accidentBeanForm.getDefaultModelObject();
                if (null != accidentBean) {
                    try {
                        accidentBean.setAccidentType(selectedAccidentType);
                        accidentBean.setAccidentLevel(selectedAccidentLevel);
                        accidentBean.setIndustryCategory(selectedIndustryCategory);
                        accidentBean.setAccidentPreliminaryAnalysis(selectedAccidentPreliminaryAnalysis);
                        accidentBean.setAccidentDescribe(accidentDescribe.getModelObject());
//                        accidentBean.setAccidentTime(accidentTime.getDefaultModelObjectAsString());
                        accidentBeanService.saveEntity(accidentBean);
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

    private void initSelect(List<String> SEARCH_ENGINES,String name,String selected){
        DropDownChoice<String> listSites = new DropDownChoice<String>(
                name, new PropertyModel<String>(this,selected), SEARCH_ENGINES);
        accidentBeanForm.add(listSites);
    }

    private void addSelectToForm(){
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"accidentType","selectedAccidentType");
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"accidentLevel","selectedAccidentLevel");
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"industryCategory","selectedIndustryCategory");
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"accidentPreliminaryAnalysis","selectedAccidentPreliminaryAnalysis");
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        accidentBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("signer");
        addTextFieldToForm("liaisonsPhone");
        addTextFieldToForm("liaisons");
        addTextFieldToForm("operatorPhone");
        addTextFieldToForm("operator");
        addTextFieldToForm("videoLink");
        addTextFieldToForm("attachment");
        addTextFieldToForm("measure");
//        addTextFieldToForm("describe");
        addTextFieldToForm("headcountEvacuees");
        addTextFieldToForm("headcountTrappedOrMissing");
        addTextFieldToForm("headcountSlight");
        addTextFieldToForm("headcountSerious");
        addTextFieldToForm("headcountDeath");
        addTextFieldToForm("accidentScene");
        addTextFieldToForm("economicLosses");
//        addTextFieldToForm("accidentPreliminaryAnalysis");
//        addTextFieldToForm("detailsPlace");
        addTextFieldToForm("place");
//        addTextFieldToForm("industryCategory");
//        addTextFieldToForm("accidentLevel");
        //addTextFieldToForm("accidentType");
        addTextFieldToForm("accidentTime");
    }
}
