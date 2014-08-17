package com.daren.accident.webapp.wicket.page;

import com.daren.accident.api.biz.IAccidentBeanService;
import com.daren.accident.entities.AccidentBean;
import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * Created by Dell on 14-8-12.
 */
public class AccidentEditPage extends BasePanel {

    final WebMarkupContainer dialogWrapper;
    AccidentBean accidentEditPageAccidentBean = new AccidentBean();
    Form<AccidentBean> accidentBeanForm;
    IrisAbstractDialog dialog;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IAccidentBeanService accidentBeanService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;

    public AccidentEditPage(final String id, final WebMarkupContainer wmc, AccidentBean bean) {
        super(id, wmc);
        if (bean != null) {
            this.accidentEditPageAccidentBean = bean;
        }
        accidentBeanForm = new Form("accidentForm", new CompoundPropertyModel(accidentEditPageAccidentBean));
        this.add(accidentBeanForm);
        addForm();
        initFeedBack();

        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog"));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
    }

    public void addForm() {
        addTextFieldsToForm();
        addSelectToForm();
        accidentBeanForm.add(initGisButton());
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
                try {
                    accidentBeanService.saveEntity(accidentEditPageAccidentBean);
                    feedbackPanel.info("保存成功！");
                    target.add(feedbackPanel);
                    onSaveOnclick(accidentEditPageAccidentBean, target);
                } catch (Exception e) {
                    feedbackPanel.info("保存失败！");
                    target.add(feedbackPanel);
                }
            }
        };
        accidentBeanForm.add(ajaxSubmitLink);

        accidentBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    public void onSaveOnclick(AccidentBean bean, AjaxRequestTarget target) {
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        accidentBeanForm.add(listSites);
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        accidentBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("accidentType", IDictConstService.ACCIDENT_TYPE);
        initSelect("accidentLevel", IDictConstService.ACCIDENT_LEVEL);
        initSelect("industryCategory", IDictConstService.INDUSTRY_CATEGORY);
        initSelect("accidentPreliminaryAnalysis", IDictConstService.ACCIDENT_PRELIMINARY_ANALYSIS);
        initSelect("accidentUnit", enterpriseBeanService.getAllBeansToHashMap());
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        accidentBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        accidentBeanForm.add(hiddenField);
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
        addHiddenFieldToForm("lng");
        addHiddenFieldToForm("lat");
    }

    private void createDialog(AjaxRequestTarget target, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new MapPage("dialog", title, null) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {

            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    private AjaxLink initGisButton() {
        AjaxLink alink = new AjaxLink("gisSrc") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "标注地址");
            }
        };
        return alink;
    }
}
