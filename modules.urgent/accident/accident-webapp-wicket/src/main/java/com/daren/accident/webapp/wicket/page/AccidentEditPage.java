package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentEditPage extends BasePanel {

    @Inject
    private IAccidentBeanService accidentBeanService;

    //注入字典业务服务
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;

    AccidentBean accidentEditPageAccidentBean = new AccidentBean();
    Form<AccidentBean> accidentBeanForm;

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    private Map<String, String> accidentTypeMap;

    public AccidentEditPage(final String id, final WebMarkupContainer wmc, AccidentBean bean) {
        super(id, wmc);
        if (bean != null) {
            this.accidentEditPageAccidentBean = (AccidentBean) accidentBeanService.getEntity(bean.getId());
        }
        accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(accidentEditPageAccidentBean));
        this.add(accidentBeanForm);
        addForm();
        initFeedBack();
        accidentTypeMap = dictBeanService.getDictMap(IDictConstService.ACCIDENT_TYPE);
    }

    public void addForm() {
        addTextFieldsToForm();
        addSelectToForm();

        //多行文本
        final TextArea<String> accidentDescribe = new TextArea<String>("accidentDescribe");
        accidentBeanForm.add(accidentDescribe);
        accidentEditPageAccidentBean.setAccidentTime(new Date());

        //日期控件//
        final DatePicker accidentTime = new DatePicker("accidentTime",
                new PropertyModel<Date>(accidentEditPageAccidentBean, "accidentTime"), "yyyy-MM-dd",
                new Options("dateFormat", Options.asString("yy-mm-dd")));
        accidentBeanForm.add(accidentTime);

        AjaxButton ajaxSubmitLink = new AjaxButton("save", accidentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AccidentBean bean = accidentBeanForm.getModelObject();
                if (null != bean) {
                    try {
                        accidentBeanService.saveEntity(accidentEditPageAccidentBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                        onSaveOnclick(bean ,target);
                    } catch (Exception e) {
                        target.appendJavaScript("alert('保存失败')");
                    }
                }
            }
        };
        accidentBeanForm.add(ajaxSubmitLink);

        accidentBeanForm.add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
    }

    public void onSaveOnclick(AccidentBean bean ,AjaxRequestTarget target) {
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initSelect( String name) {
        IModel dropDownModel = new Model() {
            public java.io.Serializable getObject() {
                return new ArrayList(accidentTypeMap.keySet());
            }
        };
        //下拉列表
        DropDownChoice<String> listSites = new DropDownChoice<String>(
                name, dropDownModel, new IChoiceRenderer() {
            public String getDisplayValue(Object object) {
                return accidentTypeMap.get(object);
            }

            public String getIdValue(Object object, int index) {
                return object.toString();
            }
        });
        accidentBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("accidentType");
        initSelect("accidentLevel");
        initSelect("industryCategory");
        initSelect("accidentPreliminaryAnalysis");
        initSelect("accidentUnit");
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
        addTextFieldToForm("headcountEvacuees");
        addTextFieldToForm("headcountTrappedOrMissing");
        addTextFieldToForm("headcountSlight");
        addTextFieldToForm("headcountSerious");
        addTextFieldToForm("headcountDeath");
        addTextFieldToForm("accidentScene");
        addTextFieldToForm("economicLosses");
        addTextFieldToForm("place");
        addTextFieldToForm("accidentTitle");
    }
}
