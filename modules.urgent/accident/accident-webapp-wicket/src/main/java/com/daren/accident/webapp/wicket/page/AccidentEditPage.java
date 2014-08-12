package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentEditPage extends BasePanel {

    final private String selectedAccidentType = "触电";
    final private String selectedAccidentLevel = "触电";
    final private String selectedIndustryCategory = "触电";
    final private String selectedAccidentPreliminaryAnalysis = "触电";
    AccidentBean accidentBean = new AccidentBean();
    Form<AccidentBean> accidentBeanForm;
    @Inject
    private IAccidentBeanService accidentBeanService;

    public AccidentEditPage(final String id, final WebMarkupContainer wmc, AccidentBean bean) {
        super(id, wmc);
        if (bean != null) {
            this.accidentBean = (AccidentBean) accidentBeanService.getEntity(bean.getId());
            ;
        }
        accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(accidentBean));
        this.add(accidentBeanForm);
        //initForm(accidentBean.getId());
        addForm();

    }

    public void addForm() {
//        accidentBeanForm.setMultiPart(true);
//        accidentBeanForm.setModelObject(accidentBean);

        addTextFieldsToForm();
        addSelectToForm();

        //多行文本

        final TextArea<String> accidentDescribe = new TextArea<String>("accidentDescribe");
        accidentBeanForm.add(accidentDescribe);

        accidentBean.setAccidentTime(new Date());

        //日期控件//
        //final DateTextField
        final DatePicker accidentTime = new DatePicker("accidentTime", new PropertyModel<Date>(accidentBean, "accidentTime"), "yyyy-MM-dd", new Options("dateFormat", Options.asString("yy-mm-dd")));
//        final  TextField accidentTime = new TextField("accidentTime");
        accidentBeanForm.add(accidentTime);

        AjaxButton ajaxSubmitLink = new AjaxButton("save", accidentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean bean = accidentBeanForm.getModelObject();
                if (null != bean) {
                    try {
                        bean.setAccidentType(selectedAccidentType);
                        bean.setAccidentLevel(selectedAccidentLevel);
                        bean.setIndustryCategory(selectedIndustryCategory);
                        bean.setAccidentPreliminaryAnalysis(selectedAccidentPreliminaryAnalysis);
                        bean.setAccidentDescribe(accidentDescribe.getModelObject());
//                        accidentBean.setAccidentTime(accidentTime.getDefaultModelObjectAsString());
                        accidentBeanService.saveEntity(accidentBean);
                        target.appendJavaScript("alert('保存成功')");
                    } catch (Exception e) {
                        target.appendJavaScript("alert('保存失败')");
                    }
                }
            }
        };
        accidentBeanForm.add(ajaxSubmitLink);
    }

    private void initForm(long accidentBeanId) {
        if (-1 != accidentBeanId) {
            AccidentBean accidentBean = (AccidentBean) accidentBeanService.getEntity(accidentBeanId);
            accidentBeanForm.setModelObject(accidentBean);
        }
    }

    private void initSelect(List<String> SEARCH_ENGINES, String name, String selected) {
        DropDownChoice<String> listSites = new DropDownChoice<String>(
                name, new PropertyModel<String>(accidentBean, selected), SEARCH_ENGINES);
        accidentBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        /*initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"accidentType","selectedAccidentType");
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"accidentLevel","selectedAccidentLevel");
        initSelect(Arrays.asList(new String[] {"物体打击", "灼烫", "触电" }),"industryCategory","selectedIndustryCategory");*/
        initSelect(Arrays.asList(new String[]{"物体打击", "灼烫", "触电"}), "accidentPreliminaryAnalysis", "accidentPreliminaryAnalysis");
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
//        addTextFieldToForm("accidentTime");
    }
}
