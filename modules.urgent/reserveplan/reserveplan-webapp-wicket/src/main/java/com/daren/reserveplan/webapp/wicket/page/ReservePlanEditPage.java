package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @类描述：预案管理
 * @创建人：wangkairan
 * @创建时间：2014-08-10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanEditPage extends BasePanel {
    @Inject
    private IReservePlanBeanService reservePlanBeanService;

    @Inject
    private IUploadDocumentService uploadDocumentService;

    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    final RepeatingView spotPlanPanel = new RepeatingView("spotPlanPanel");
    final RepeatingView specialPlanPanel = new RepeatingView("specialPlanPanel");

    public ReservePlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id, wmc);
        initForm(reservePlanBean);
        initFeedBack();
        initPlanPanel(id,wmc,reservePlanBean);
    }

    private void initPlanPanel(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean){
        specialPlanPanel.add(new SpecialPlanEditPage(specialPlanPanel.newChildId(),wmc,reservePlanBean));
        spotPlanPanel.add(new SpotPlanEditPage(spotPlanPanel.newChildId(),wmc,reservePlanBean));
        add(specialPlanPanel);
        add(spotPlanPanel);
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
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
        final FileUploadField uploadFieldComprehensive = new FileUploadField("comprehensivePlanId");
        form.add(uploadFieldApply);
        form.add(uploadFieldRegister);
        form.add(uploadFieldReview);
        form.add(uploadFieldExpert);
        form.add(uploadFieldComprehensive);

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
                reservePlanBean.setComprehensivePlanId(saveDocument(uploadFieldComprehensive));
                reservePlanBeanService.saveEntity(reservePlanBean);
                super.onSubmit(target, form);
                feedbackPanel.info("保存成功！");
                target.add(feedbackPanel);
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