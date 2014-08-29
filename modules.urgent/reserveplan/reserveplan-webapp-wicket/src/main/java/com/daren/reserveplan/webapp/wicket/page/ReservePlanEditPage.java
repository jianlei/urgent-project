package com.daren.reserveplan.webapp.wicket.page;

import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.wicket.BasePanel;
import com.daren.enterprise.api.biz.IEnterpriseBeanService;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.IReservePlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
import java.util.Map;

/**
 * @类描述：预案管理
 * @创建人：wangkairan
 * @创建时间：2014-08-10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ReservePlanEditPage extends BasePanel {
    final RepeatingView spotPlanPanel = new RepeatingView("spotPlanPanel");
    final RepeatingView specialPlanPanel = new RepeatingView("specialPlanPanel");
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    Form reserveForm = new Form("createForm", new CompoundPropertyModel(new ReservePlanBean()));
    @Inject
    private IReservePlanBeanService reservePlanBeanService;
    @Inject
    private IUploadDocumentService uploadDocumentService;
    @Inject
    private IEnterpriseBeanService enterpriseBeanService;


    public ReservePlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id, wmc);
        initForm(reservePlanBean);
        initFeedBack();
        addSelectToForm();
    }


    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initForm(final ReservePlanBean reservePlanBean) {
        //添加表单

        reserveForm.setMultiPart(true);
        this.add(reserveForm);
        //初始化FORM对象
        if (null != reservePlanBean) {
            reserveForm.setModelObject(reservePlanBean);
        }
        //添加上传控件
        final FileUploadField uploadFieldApply = new FileUploadField("reservePlanApplyId");
        final FileUploadField uploadFieldRegister = new FileUploadField("reservePlanRegisterId");
        final FileUploadField uploadFieldReview = new FileUploadField("reviewCommentId");
        final FileUploadField uploadFieldExpert = new FileUploadField("reviewExpertId");
        final FileUploadField uploadFieldComprehensive = new FileUploadField("comprehensivePlanId");
        reserveForm.add(uploadFieldApply);
        reserveForm.add(uploadFieldRegister);
        reserveForm.add(uploadFieldReview);
        reserveForm.add(uploadFieldExpert);
        reserveForm.add(uploadFieldComprehensive);

        reserveForm.add(new TextField("description"));
        reserveForm.add(new TextField("name"));
        reserveForm.add(new TextField("mark"));
        reserveForm.add(new TextField("level"));
//        form.add(new TextField("type"));

        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", reserveForm) {
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
        reserveForm.add(ajaxSubmitLinkCreate);

        reserveForm.add(new AjaxButton("cancel") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onDeleteTabs(target);
            }
        });
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

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }


    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        reserveForm.add(listSites);
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        reserveForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("enterpriseId", enterpriseBeanService.getAllBeansToHashMap());
    }
}