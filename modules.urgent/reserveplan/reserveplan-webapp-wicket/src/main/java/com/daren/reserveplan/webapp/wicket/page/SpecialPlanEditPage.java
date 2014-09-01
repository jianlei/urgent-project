package com.daren.reserveplan.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.api.IConst;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.ISpecialPlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.daren.reserveplan.entities.SpecialPlanBean;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 14-8-11.
 */
public class SpecialPlanEditPage extends BasePanel {

    final WebMarkupContainer table = new WebMarkupContainer("table");
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    SpecialPlanDataProvider provider = new SpecialPlanDataProvider();
    Form specialPlanForm;
    @Inject
    private IUploadDocumentService uploadDocumentService;
    @Inject
    private ISpecialPlanBeanService specialPlanService;

    public SpecialPlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id, wmc);
        initForm(reservePlanBean);
        initFeedBack();
        initTable(reservePlanBean);
        addSelectToForm();
    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initTable(final ReservePlanBean reservePlanBean) {

        add(table.setOutputMarkupId(true));
        provider.setSpecialPlanBean(reservePlanBean);
        final DataView<SpecialPlanBean> specialPlanBeanDataView = new DataView<SpecialPlanBean>("specialPlanRows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<SpecialPlanBean> specialPlanItem) {
                final SpecialPlanBean specialPlanBean = specialPlanItem.getModelObject();
                specialPlanItem.add(new Label("specialPlanName", specialPlanBean.getName()));
                specialPlanItem.add(new Label("specialPlanDescription", specialPlanBean.getDescription()));
                specialPlanItem.add(new Label("specialPlanType", specialPlanBean.getType()));

                addDownLoadLink(specialPlanItem, "specialPlanDocumentId", specialPlanBean.getSpecialPlanDocumentId());
                addDeleteLink(specialPlanItem, "delete", specialPlanBean, table);
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("specialPlanNavigator", specialPlanBeanDataView) {
        };
        table.add(pagingNavigator);
        table.add(specialPlanBeanDataView);
    }

    private void initForm(final ReservePlanBean reservePlanBean) {
        //添加表单
        specialPlanForm = new Form("specialPlanForm", new CompoundPropertyModel(new SpecialPlanBean()));
        specialPlanForm.setMultiPart(true);
        this.add(specialPlanForm);

        //添加上传控件
        final FileUploadField fileUploadFieldSpecial = new FileUploadField("specialPlanDocumentId");

        specialPlanForm.add(new TextField("description"));
        specialPlanForm.add(new TextField("name"));
        specialPlanForm.add(fileUploadFieldSpecial);

        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", specialPlanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                SpecialPlanBean specialPlanBean = (SpecialPlanBean) form.getDefaultModelObject();
                specialPlanBean.setSpecialPlanDocumentId(saveDocument(fileUploadFieldSpecial));
                specialPlanBean.setReservePlanId(reservePlanBean.getId());
                specialPlanService.saveEntity(specialPlanBean);
                super.onSubmit(target, form);
                feedbackPanel.info("专项预案保存成功！");
                target.add(feedbackPanel);
                target.add(table);
                specialPlanForm.setModelObject(new SpecialPlanBean());
                target.add(specialPlanForm);
            }
        };
        specialPlanForm.add(ajaxSubmitLinkCreate);
        specialPlanForm.add(new AjaxButton("cancel") {
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
                    String path = IConst.OFFICE_WEB_PATH_WRITE + fileUpload.getMD5();
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

    private void addDownLoadLink(Item item, String downLoadLinkName, final String fileName, final String filePath) {
        DownloadLink downloadLink = new DownloadLink(downLoadLinkName, new AbstractReadOnlyModel<java.io.File>() {
            private static final long serialVersionUID = 1L;

            @Override
            public java.io.File getObject() {
                java.io.File tempFile = null;
                FileInputStream fileInputStream = null;
                try {
                    tempFile = new java.io.File(fileName);
                    fileInputStream = new FileInputStream(filePath);
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return tempFile;
            }
        });
        item.add(downloadLink.setOutputMarkupId(true).add());
    }

    private void addDownLoadLink(Item item, String downLoadLinkName) {
        item.add(new Label(downLoadLinkName, "未上传"));
    }

    private void addDownLoadLink(Item item, String downLoadLinkName, String documentId) {
        long documentIdLong;
        if (null != documentId && !"".equals(documentId)) {
            documentIdLong = Long.parseLong(documentId);
        } else {
            documentIdLong = 0;
        }
        if (documentIdLong > 0) {
            DocumentBean documentBean = (DocumentBean) uploadDocumentService.getEntity(Long.parseLong(documentId));
            addDownLoadLink(item, downLoadLinkName, documentBean.getName(), documentBean.getFilePath());
        } else {
            addDownLoadLink(item, downLoadLinkName);
        }
    }

    private void addDeleteLink(Item<SpecialPlanBean> item, String linkName, final SpecialPlanBean specialPlanBean, final WebMarkupContainer table) {
        AjaxLink ajaxLink = new AjaxLink(linkName) {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
                super.updateAjaxAttributes(attributes);
                AjaxCallListener listener = new AjaxCallListener();
                listener.onPrecondition("if(!confirm('确定要删除吗')){return false;}");
                attributes.getAjaxCallListeners().add(listener);
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                specialPlanService.deleteEntity(specialPlanBean.getId());
                target.add(table);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        specialPlanForm.add(listSites);
    }

    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        specialPlanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("type", IDictConstService.ACCIDENT_TYPE);
    }

    class SpecialPlanDataProvider extends ListDataProvider<SpecialPlanBean> {
        private SpecialPlanBean specialPlanBean = null;

        public void setSpecialPlanBean(ReservePlanBean reservePlanBean) {
            if (null != reservePlanBean) {
                this.specialPlanBean = new SpecialPlanBean();
                this.specialPlanBean.setReservePlanId(reservePlanBean.getId());
            }
        }

        @Override
        protected List<SpecialPlanBean> getData() {
            if (specialPlanBean == null) {
                return new ArrayList<>();
            } else {
                return specialPlanService.queryByReservePlanId(specialPlanBean);
            }
        }
    }
}
