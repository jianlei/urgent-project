package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
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

public class ReservePlanEditPage extends BasePanel {
    @Inject
    private IReservePlanBeanService reservePlanBeanService;

    @Inject
    private IUploadDocumentService uploadDocumentService;

    public ReservePlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id,wmc);
        final FileUploadField fileUploadField = new FileUploadField("filePath");
        Form form = new Form("createForm", new CompoundPropertyModel(new DocumentBean()));
        form.setMultiPart(true);
        this.add(form);
        form.add(fileUploadField);
        form.add(new TextField("description"));

        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                DocumentBean documentBean = (DocumentBean) form.getDefaultModelObject();
                try {
                    List<FileUpload> fileUploadList = fileUploadField.getFileUploads();
                    if (null != fileUploadList && fileUploadList.size() > 0) {
                        for (FileUpload fileUpload : fileUploadList) {
                            String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
                            File file = new File(path);
                            fileUpload.writeTo(file);
                            documentBean.setFilePath(path);
                            documentBean.setName(fileUpload.getClientFileName());
                            documentBean.setSize(fileUpload.getSize());
                            documentBean.setType(fileUpload.getContentType());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(reservePlanBean == null){
                    ReservePlanBean reservePlanBeanLocal = new ReservePlanBean();
                    reservePlanBeanLocal.setName(documentBean.getName());
                    reservePlanBeanLocal = (ReservePlanBean)reservePlanBeanService.saveEntityAndReturn(reservePlanBeanLocal);
                    documentBean.setAttach(reservePlanBeanLocal.getId());
                    uploadDocumentService.saveEntity(documentBean);
                }else{
                    reservePlanBean.setName(documentBean.getName());
                    reservePlanBeanService.saveEntity(reservePlanBean);
                    documentBean.setAttach(reservePlanBean.getId());
                    uploadDocumentService.saveEntity(documentBean);
                }
                super.onSubmit(target, form);
                target.appendJavaScript("alert('保存成功')");
            }
        };
        add(ajaxSubmitLinkCreate);
    }
}