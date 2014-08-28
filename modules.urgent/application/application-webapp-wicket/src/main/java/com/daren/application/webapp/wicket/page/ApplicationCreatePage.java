package com.daren.application.webapp.wicket.page;


import com.daren.application.api.biz.IApplicationBeanService;
import com.daren.application.entities.ApplicationBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
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
import java.util.List;

/**
 * @类描述：品牌维护
 * @创建人：sunlf
 * @创建时间：2014-03-29 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ApplicationCreatePage extends BasePanel {
    @Inject
    private IApplicationBeanService applicationBeanService;

    @Inject
    private IUploadDocumentService uploadDocumentService;

    public ApplicationCreatePage(final String id, final WebMarkupContainer wmc, final ApplicationBean applicationBean) {
        super(id, wmc);
        final DocumentBean documentBean = new DocumentBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("applicationCreateForm", new CompoundPropertyModel(documentBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DocumentBean documentBean1 = (DocumentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "D:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            documentBean1.setFilePath(path);
                            documentBean1.setName(fileUpload.getClientFileName());
                            documentBean1.setSize(fileUpload.getSize());
                            documentBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (applicationBean == null) {
                    ApplicationBean applicationBeanLocal = new ApplicationBean();
                    applicationBeanLocal.setName(documentBean1.getName());
                    applicationBeanLocal = (ApplicationBean) applicationBeanService.saveEntityAndReturn(applicationBeanLocal);
                    documentBean1.setAttach(applicationBeanLocal.getId());
                    uploadDocumentService.saveEntity(documentBean1);
                } else {
                    applicationBean.setName(documentBean1.getName());
                    applicationBeanService.saveEntity(applicationBean);
                    documentBean1.setAttach(applicationBean.getId());
                    uploadDocumentService.saveEntity(documentBean1);
                }


                super.onSubmit(target, form);
                target.appendJavaScript("alert('保存成功')");
            }
        };
        form.add(ajaxSubmitLinkCreate);

//        form.add(new AjaxButton("cancel") {
//            @Override
//            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//                onDeleteTabs(target);
//            }
//        });
    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }
}