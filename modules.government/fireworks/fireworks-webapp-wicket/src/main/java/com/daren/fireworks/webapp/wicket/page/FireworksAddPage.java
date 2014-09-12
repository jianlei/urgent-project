package com.daren.fireworks.webapp.wicket.page;


import com.daren.core.web.wicket.BasePanel;
import com.daren.fireworks.api.biz.IFireworksService;
import com.daren.fireworks.entities.FireworksBean;
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
 * @类描述：烟花爆竹经营(批发)许可证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksAddPage extends BasePanel {
    Form<FireworksBean> fireworksBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new FireworksBean()));
    FireworksBean fireworksBean = new FireworksBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IFireworksService fireworksService;

    public FireworksAddPage(final String id, final WebMarkupContainer wmc, final FireworksBean bean) {
        super(id, wmc);
        if (null != bean) {
            fireworksBean = bean;
        }
        initForm(fireworksBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        fireworksBeanForm.setMultiPart(true);
        this.add(fireworksBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", fireworksBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                FireworksBean fireworksBean = (FireworksBean) form.getModelObject();
                if (null != fireworksBean) {
                    try {
                        fireworksService.saveEntity(fireworksBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        fireworksBeanForm.add(ajaxSubmitLink);
        fireworksBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(FireworksBean bean) {
        fireworksBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        fireworksBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        fireworksBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("head");
        addTextFieldToForm("phone");
        addTextFieldToForm("address");
        addTextFieldToForm("economicsType");
        addTextFieldToForm("storageAddress");
        addTextFieldToForm("scope");
    }
}