package com.daren.production.webapp.wicket.page;


import com.daren.admin.api.biz.IUserBeanService;
import com.daren.core.web.wicket.BasePanel;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
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
 * @类描述：安全生产许可证(非煤矿矿山企业)
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ProductionAddPage extends BasePanel {
    Form<ProductionBean> productionBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new ProductionBean()));
    ProductionBean productionBean = new ProductionBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IProductionService productionService;
    @Inject
    private IUserBeanService userBeanService;

    public ProductionAddPage(final String id, final WebMarkupContainer wmc, final ProductionBean bean) {
        super(id, wmc);
        if (null != bean) {
            productionBean = bean;
        }
        initForm(productionBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        productionBeanForm.setMultiPart(true);
        this.add(productionBeanForm);
        addTextFieldsToForm();
        AjaxButton ajaxSubmitLink = new AjaxButton("save", productionBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ProductionBean productionBean = (ProductionBean) form.getModelObject();
                if (null != productionBean) {
                    try {
                        productionBean.setLoginName(userBeanService.getCurrentUserLoginName());
                        productionService.saveEntity(productionBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        productionBeanForm.add(ajaxSubmitLink);
        productionBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(ProductionBean bean) {
        productionBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        productionBeanForm.add(textField);
    }

    private void addHiddenFieldToForm(String value) {
        HiddenField hiddenField = new HiddenField(value);
        productionBeanForm.add(hiddenField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("head");
        addTextFieldToForm("phone");
        addTextFieldToForm("address");
        addTextFieldToForm("economicsType");
        addTextFieldToForm("scope");
    }
}