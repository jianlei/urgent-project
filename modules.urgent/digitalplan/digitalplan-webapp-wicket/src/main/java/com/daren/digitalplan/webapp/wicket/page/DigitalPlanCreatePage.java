package com.daren.digitalplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.digitalplan.api.biz.IDigitalPlanBeanService;
import com.daren.digitalplan.entities.DigitalPlanBean;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @类描述：重大危险源管理
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DigitalPlanCreatePage extends BasePanel {

    Form<DigitalPlanBean> digitalplanBeanForm = new Form("digitalPlanForm", new CompoundPropertyModel(new DigitalPlanBean()));
    DigitalPlanBean digitalplanBean = new DigitalPlanBean();
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    @Inject
    private IDigitalPlanBeanService digitalplanBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;


    public DigitalPlanCreatePage(final String id, final WebMarkupContainer wmc, final DigitalPlanBean bean) {
        super(id, wmc);
        if (null != bean) {
            digitalplanBean = bean;
        }
        initForm(digitalplanBean);
        initFeedBack();
        addForm(id, wmc);
    }

    public void addForm(final String id, final WebMarkupContainer wmc) {

        digitalplanBeanForm.setMultiPart(true);
        this.add(digitalplanBeanForm);

        addTextFieldsToForm();

        final FileUploadField uploadFieldDigitalPlan = new FileUploadField("digitalPlanId");
        digitalplanBeanForm.add(uploadFieldDigitalPlan);

        AjaxButton ajaxSubmitLink = new AjaxButton("save", digitalplanBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DigitalPlanBean digitalplanBean = (DigitalPlanBean) digitalplanBeanForm.getDefaultModelObject();
                if (null != digitalplanBean) {
                    try {
                        digitalplanBean.setUpdateDate(new Date());
                        digitalplanBean.setDigitalPlanId(saveDocument(uploadFieldDigitalPlan));
                        digitalplanBeanService.saveEntity(digitalplanBean);
                        feedbackPanel.info("保存成功！");
                        target.add(feedbackPanel);
                    } catch (Exception e) {
                        feedbackPanel.info("保存失败！");
                        target.add(feedbackPanel);
                    }
                }
            }
        };
        digitalplanBeanForm.add(ajaxSubmitLink);
        digitalplanBeanForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    private void initForm(DigitalPlanBean bean) {
        digitalplanBeanForm.setModelObject(bean);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void addTextFieldToForm(String value) {
        TextField textField = new TextField(value);
        digitalplanBeanForm.add(textField);
    }

    private void addTextFieldsToForm() {
        addTextFieldToForm("name");
        addTextFieldToForm("description");
        addTextFieldToForm("level");
        addTextFieldToForm("type");
    }


    private String saveDocument(FileUploadField uploadFieldApply) {
        DocumentBean documentBean = new DocumentBean();
        List<FileUpload> fileUploadList = uploadFieldApply.getFileUploads();
        try {
            if (null != fileUploadList && fileUploadList.size() > 0) {
                for (FileUpload fileUpload : fileUploadList) {
                    String path = "D:\\saveFilePath\\" + fileUpload.getMD5();
                    File file = new File(path);
                    fileUpload.writeTo(file);
                    documentBean.setFilePath(path);
                    documentBean.setName(fileUpload.getClientFileName());
                    documentBean.setSize(fileUpload.getSize());
                    documentBean.setType(fileUpload.getContentType());
                    uploadDocumentService.saveEntity(documentBean);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documentBean.getId() + "";
    }
}