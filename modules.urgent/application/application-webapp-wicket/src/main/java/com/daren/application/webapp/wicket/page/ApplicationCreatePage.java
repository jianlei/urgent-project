package com.daren.application.webapp.wicket.page;


import com.daren.application.api.biz.IApplicationBeanService;
import com.daren.application.entities.ApplicationBean;
import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocmentBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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

    public ApplicationCreatePage(final String id,final WebMarkupContainer wmc,final ApplicationBean applicationBean) {
        super(id,wmc);
        final DocmentBean docmentBean = new DocmentBean();
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("applicationCreateForm", new CompoundPropertyModel(docmentBean));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DocmentBean docmentBean1 = (DocmentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            docmentBean1.setFilePath(path);
                            docmentBean1.setName(fileUpload.getClientFileName());
                            docmentBean1.setSize(fileUpload.getSize());
                            docmentBean1.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(applicationBean == null){
                    ApplicationBean applicationBeanLocal = new ApplicationBean();
                    applicationBeanLocal.setName(docmentBean1.getName());
                    applicationBeanLocal = (ApplicationBean)applicationBeanService.saveEntityAndReturn(applicationBeanLocal);
                    docmentBean1.setAttach(applicationBeanLocal.getId());
                    uploadDocumentService.saveEntity(docmentBean1);
                }else{
                    applicationBean.setName(docmentBean1.getName());
                    applicationBeanService.saveEntity(applicationBean);
                    docmentBean1.setAttach(applicationBean.getId());
                    uploadDocumentService.saveEntity(docmentBean1);
                }


                super.onSubmit(target, form);
                target.appendJavaScript("alert('保存成功')");
            }
        };
        add(ajaxSubmitLinkCreate);
    }
}