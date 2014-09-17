package com.daren.attachment.webapp.wicket.page;

import com.daren.attachment.api.biz.IAttachmentService;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.api.IConst;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2014/8/26.
 */
public class UploadPage extends Panel {
    @Inject
    @Reference(id = "attachmentService", serviceInterface = IAttachmentService.class)
    private IAttachmentService attachmentService;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    public UploadPage(String id, final long preateId, final String appType) {
        super(id);
        final AttachmentBean attachmentBean = new AttachmentBean();
        final FileUploadField fileUploadField = new FileUploadField("path");
        Form form = new Form("form", new CompoundPropertyModel(attachmentBean));
        form.setMultiPart(true);
        this.add(form);
        this.add(feedbackPanel);
        form.add(fileUploadField);

        //保存按钮
        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                AttachmentBean formBean = (AttachmentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    File folder =new File(IConst.OFFICE_WEB_PATH_WRITE +appType+preateId);
                    if  (!folder.exists() && !folder.isDirectory()){
                        folder .mkdir();
                    }
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = IConst.OFFICE_WEB_PATH_WRITE +appType+preateId+"\\"+ fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            formBean.setName(fileUpload.getClientFileName());
                            formBean.setPath(path);
                            formBean.setType(fileUpload.getContentType());
                            formBean.setParentId(preateId);
                            formBean.setAppType(appType);
                        }
                    }
                    feedbackPanel.info("上传成功！");
                    target.add(feedbackPanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                attachmentService.saveEntity(formBean);
                super.onSubmit(target, form);
            }
        };
        form.add(ajaxSubmitLinkCreate);
    }
}
