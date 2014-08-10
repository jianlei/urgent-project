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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
    FeedbackPanel feedbackPanel = new FeedbackPanel("feedBack");
    @Inject
    private IReservePlanBeanService reservePlanBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;

    public ReservePlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id, wmc);
        initForm(reservePlanBean);
    }

    private void initForm(final ReservePlanBean reservePlanBean) {
        //添加表单
        Form form = new Form("createForm", new CompoundPropertyModel(new ReservePlanBean()));
        form.setMultiPart(true);
        this.add(form);
        //初始化FORM对象
        if (null != reservePlanBean) {
            form.setModelObject(reservePlanBean);
        }
        //添加上传控件
        final FileUploadField uploadFieldApply = new FileUploadField("reservePlanApplyId");
        final FileUploadField uploadFieldRegister = new FileUploadField("reservePlanRegisterId");
        final FileUploadField uploadFieldReview = new FileUploadField("reviewCommentId");
        final FileUploadField uploadFieldExpert = new FileUploadField("reviewExpertId");
        form.add(uploadFieldApply);
        form.add(uploadFieldRegister);
        form.add(uploadFieldReview);
        form.add(uploadFieldExpert);

        form.add(new TextField("description"));
        form.add(new TextField("name"));

        AjaxSubmitLink ajaxSubmitLinkCreate = new AjaxSubmitLink("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ReservePlanBean reservePlanBean = (ReservePlanBean) form.getDefaultModelObject();
                reservePlanBean.setReservePlanApplyId(saveDocument(uploadFieldApply));
                reservePlanBean.setReservePlanRegisterId(saveDocument(uploadFieldRegister));
                reservePlanBean.setReviewCommentId(saveDocument(uploadFieldReview));
                reservePlanBean.setReviewExpertId(saveDocument(uploadFieldExpert));
                reservePlanBeanService.saveEntity(reservePlanBean);
                super.onSubmit(target, form);
                target.appendJavaScript("alert('保存成功')");
            }
        };

        add(ajaxSubmitLinkCreate);
    }

    private String saveDocument(FileUploadField uploadFieldApply) {
        DocumentBean documentBean = new DocumentBean();
        List<FileUpload> fileUploadList = uploadFieldApply.getFileUploads();
        try {
            if (null != fileUploadList && fileUploadList.size() > 0) {
                for (FileUpload fileUpload : fileUploadList) {
                    String path = "F:\\saveFilePath\\" + fileUpload.getMD5();
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