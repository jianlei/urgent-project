package com.daren.expert.webapp.wicket.page;

import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.core.web.wicket.component.dialog.IrisAbstractDialog;
import com.daren.expert.api.biz.IEnterpriseExpertBeanService;
import com.daren.expert.entities.EnterpriseExpertBean;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

/**
 * Created by Administrator on 14-8-5.
 */
public class EnterpriseExpertAddPage extends Panel {
    final WebMarkupContainer dialogWrapper;
    private final String type;//操作类型 ：新增(add) 或编辑（edit）
    IrisAbstractDialog dialog;
    private boolean isAdd;
    @Inject
    @Reference(id = "enterpriseExpertBeanService", serviceInterface = IEnterpriseExpertBeanService.class)
    private IEnterpriseExpertBeanService enterpriseExpertBeanService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public EnterpriseExpertAddPage(String id, String type, IModel<EnterpriseExpertBean> model) {
        super(id, model);
        this.type = type;
        if (model.getObject() == null) {
            isAdd = true;
            initForm(Model.of(new EnterpriseExpertBean()));
        } else {
            isAdd = false;
            initForm(model);
        }
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

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(IModel<EnterpriseExpertBean> model) {
        final Form<EnterpriseExpertBean> dictForm = new Form("dictForm", new CompoundPropertyModel(model));
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        dictForm.add(feedbackPanel.setOutputMarkupId(true));
        dictForm.add(new TextField("name").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        //日期控件//
        final DatePicker accidentTime = new DatePicker("date", "yyyy-MM-dd",
                new Options("dateFormat", Options.asString("yy-mm-dd")));
        dictForm.add(accidentTime);
        dictForm.add(new TextField("sex").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("skillTitle").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("degree").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("nation").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("type").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("city").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("address").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("tel").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("phone").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("eMail").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("language").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("domain").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new TextField("direction").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new HiddenField("longitude").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new HiddenField("latitude").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        dictForm.add(new AjaxButton("save", dictForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    EnterpriseExpertBean dictBean = (EnterpriseExpertBean) form.getModelObject();
                    dictBean.setDate(accidentTime.getModelObject());
                    dictBean.setAttach(1l);
                    enterpriseExpertBeanService.saveEntity(dictBean);
                    if (isAdd) {
                        dictForm.setModelObject(new EnterpriseExpertBean());
                    }
                    feedbackPanel.info("保存成功！");
                    target.add(form);
                } catch (Exception e) {
                    feedbackPanel.error("保存失败！");
                    e.printStackTrace();
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });

        dictForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
        dictForm.setOutputMarkupId(true);
//        dictForm.add(new JSR303FormValidator());
        add(dictForm);
        dictForm.add(initGisButton());
    }

    private void createDialog(AjaxRequestTarget target, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new EnterpriseMapPage("dialog", title, null) {
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