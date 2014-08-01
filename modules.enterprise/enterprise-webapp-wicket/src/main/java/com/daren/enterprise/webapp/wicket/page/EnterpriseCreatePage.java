package com.daren.enterprise.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.enterprise.entities.EnterpriseBean;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.converter.DateConverter;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class EnterpriseCreatePage extends BasePanel {

    @Inject
    private IEnterpriseBeanService enterpriseBeanService;
    Form<EnterpriseBean> enterpriseBeanForm = new Form("enterpriseForm", new CompoundPropertyModel(new EnterpriseBean()));

    public EnterpriseCreatePage(final String id, final WebMarkupContainer wmc, long enterpriseBeanId) {
        super(id, wmc);
        initForm(enterpriseBeanId);
        addForm(id,wmc);
    }

    private void addForm(final String id, final WebMarkupContainer wmc) {

        enterpriseBeanForm.setMultiPart(true);
        this.add(enterpriseBeanForm);

        addTextFieldsToForm();

        AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("save", enterpriseBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                EnterpriseBean enterpriseBean = (EnterpriseBean) form.getDefaultModelObject();
                if (null != enterpriseBean) {
                    try {
                        enterpriseBean.setUpdateDate(new Date());
                        enterpriseBeanService.saveEntity(enterpriseBean);
                        target.appendJavaScript("alert('保存成功')");
                        wmc.removeAll();
                        wmc.addOrReplace(new EnterprisePage(id, wmc));
                        target.add(wmc);
                    } catch (Exception e) {
                        //log.error("保存企业信息异常，" +e.toString());
                        target.appendJavaScript("alert('保存失败')");
                    }
                }
            }
        };
        add(ajaxSubmitLink);
    }

    private void initForm(long enterpriseBeanId) {
        if (-1 != enterpriseBeanId) {
            EnterpriseBean enterpriseBean = (EnterpriseBean) enterpriseBeanService.getEntity(enterpriseBeanId);
            enterpriseBeanForm.setModelObject(enterpriseBean);
        }
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        enterpriseBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("qymc");
        addTextFieldToForm("frdb");
        addTextFieldToForm("cl_sj");
        addTextFieldToForm("address_zc");
        addTextFieldToForm("address_jy");
        addTextFieldToForm("postcode");
        addTextFieldToForm("qylxfs");
        addTextFieldToForm("mailaddress");
        addTextFieldToForm("zyjyxm");
        addTextFieldToForm("zyyl");
        addTextFieldToForm("zycp");
        addTextFieldToForm("jgjgdm");
        addTextFieldToForm("zzjgdm");
        addTextFieldToForm("sdlx");
        addTextFieldToForm("jjlxbm");
        addTextFieldToForm("cyrs");
        addTextFieldToForm("zcaqgcsrs");
        addTextFieldToForm("aqrs");
        addTextFieldToForm("jzaqrs");
        addTextFieldToForm("hyml_dm");
        addTextFieldToForm("hylbbm");
        addTextFieldToForm("xjqybj");
        addTextFieldToForm("sjqyid");
        addTextFieldToForm("hyzxl_dm");
        addTextFieldToForm("qygmbm");
        addTextFieldToForm("gmqk");
        addTextFieldToForm("zczc");
        addTextFieldToForm("jgfl");
        addTextFieldToForm("sndxssr");
        addTextFieldToForm("aqscfy");
        addTextFieldToForm("yazlqy_bj");
        addTextFieldToForm("zdxfdw_bj");
        addTextFieldToForm("zybbj");
        addTextFieldToForm("aqjgszqk");
        addTextFieldToForm("dlwz");
    }

}