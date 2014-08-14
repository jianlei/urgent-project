package com.daren.equipment.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.equipment.api.biz.IEquipmentBeanService;
import com.daren.equipment.entities.EquipmentBean;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EquipmentCreatePage extends BasePanel {

    @Inject
    private IEquipmentBeanService equipmentBeanService;

    Form<EquipmentBean> equipmentBeanForm = new Form("majorHazardSourceForm", new CompoundPropertyModel(new EquipmentBean()));

    EquipmentBean equipmentBean = new EquipmentBean();

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    public EquipmentCreatePage(final String id, final WebMarkupContainer wmc, final EquipmentBean bean) {
        super(id, wmc);
        if (null != bean) {
            equipmentBean = bean;
        }
        initForm(equipmentBean);
        initFeedBack();
        addForm(id, wmc);
        addSelectToForm();
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        equipmentBeanForm.setMultiPart(true);
        this.add(equipmentBeanForm);

        addTextFieldsToForm();

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("save", equipmentBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EquipmentBean equipmentBean = (EquipmentBean) equipmentBeanForm.getDefaultModelObject();
                if (null != equipmentBean) {
                    try {
                        equipmentBean.setUpdateDate(new Date());
                        equipmentBeanService.saveEntity(equipmentBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        add(ajaxSubmitLink);
        add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(EquipmentBean bean) {
        equipmentBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    //通过字典初始化下拉列表
    private void initSelect(String name,String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name,dictConst);
        equipmentBeanForm.add(listSites);
    }
    //通过Map初始化下拉列表
    private void initSelect(String name,Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name,typeMap);
        equipmentBeanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("property", IDictConstService.EQUIPMENT_PROPERTY);
        initSelect("registrationType", IDictConstService.REGISTRATION_TYPE);
        initSelect("equipmentSources", IDictConstService.EQUIPMENT_SOURCES);
        initSelect("equipmentType", IDictConstService.REGISTRATION_TYPE);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        equipmentBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
//        addTextFieldToForm("property");
//        addTextFieldToForm("registrationType");
        addTextFieldToForm("rescueId");
        addTextFieldToForm("unitName");
//        addTextFieldToForm("equipmentSources");
//        addTextFieldToForm("equipmentType");
        addTextFieldToForm("parametersSpecifications");
        addTextFieldToForm("measuringUnit");
        addTextFieldToForm("amount");
        addTextFieldToForm("regularMaintenanceInterval");
        addTextFieldToForm("durableYears");
        addTextFieldToForm("lastMaintenanceDate");
        addTextFieldToForm("manufacturer");
        addTextFieldToForm("manufactureDate");
        addTextFieldToForm("purchaseDate");
        addTextFieldToForm("unitFax");
        addTextFieldToForm("principal");
        addTextFieldToForm("officePhone");
        addTextFieldToForm("homePhone");
        addTextFieldToForm("mobilePhone");
        addTextFieldToForm("describeOrPurposes");
        addTextFieldToForm("warehouse");
        addTextFieldToForm("storagePlace");
        addTextFieldToForm("img");
        addTextFieldToForm("remark");
    }

}