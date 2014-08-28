package com.daren.reserveplan.webapp.wicket.page;

import com.daren.admin.api.biz.IDictConstService;
import com.daren.core.web.component.form.IrisDropDownChoice;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.file.api.biz.IUploadDocumentService;
import com.daren.file.entities.DocumentBean;
import com.daren.reserveplan.api.biz.ISpotPlanBeanService;
import com.daren.reserveplan.entities.ReservePlanBean;
import com.daren.reserveplan.entities.SpotPlanBean;
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

/**
 * Created by Dell on 14-8-11.
 */
public class SpotPlanEditPage extends BasePanel {

    final WebMarkupContainer table = new WebMarkupContainer("table");
    final Form spotPlanForm = new Form("spotPlanForm", new CompoundPropertyModel(new SpotPlanBean()));
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");
    SpotPlanDataProvider provider = new SpotPlanDataProvider();
    @Inject
    private IUploadDocumentService uploadDocumentService;
    @Inject
    private ISpotPlanBeanService spotPlanService;

    public SpotPlanEditPage(final String id, final WebMarkupContainer wmc, final ReservePlanBean reservePlanBean) {
        super(id, wmc);
        initForm(reservePlanBean);
        initFeedBack();
        initTable(reservePlanBean);

    }

    private void initFeedBack() {
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void initTable(final ReservePlanBean reservePlanBean) {

        add(table.setOutputMarkupId(true));
        provider.setSpotPlanBean(reservePlanBean);
        final DataView<SpotPlanBean> spotPlanBeanDataView = new DataView<SpotPlanBean>("spotPlanRows", provider, 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<SpotPlanBean> spotPlanItem) {
                final SpotPlanBean spotPlanBean = spotPlanItem.getModelObject();
                spotPlanItem.add(new Label("spotPlanName", spotPlanBean.getName()));
                spotPlanItem.add(new Label("spotPlanDescription", spotPlanBean.getDescription()));
                spotPlanItem.add(new Label("spotPlanType", spotPlanBean.getType()));

                addDownLoadLink(spotPlanItem, "spotPlanDocumentId", spotPlanBean.getSpotPlanDocumentId());
                addDeleteLink(spotPlanItem, "delete", spotPlanBean, table);
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("spotPlanNavigator", spotPlanBeanDataView) {
        };
        table.add(pagingNavigator);
        table.add(spotPlanBeanDataView);
    }

    private void initForm(final ReservePlanBean reservePlanBean) {
        addSelectToForm();

        //添加表单

        spotPlanForm.setMultiPart(true);
        this.add(spotPlanForm);

        //添加上传控件
        final FileUploadField fileUploadFieldSpecial = new FileUploadField("spotPlanDocumentId");

        spotPlanForm.add(new TextField("description"));
        spotPlanForm.add(new TextField("name"));
        spotPlanForm.add(new TextField("type"));
        spotPlanForm.add(fileUploadFieldSpecial);

        AjaxButton ajaxSubmitLinkCreate = new AjaxButton("save", spotPlanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                SpotPlanBean spotPlanBean = (SpotPlanBean) form.getDefaultModelObject();
                spotPlanBean.setSpotPlanDocumentId(saveDocument(fileUploadFieldSpecial));
                spotPlanBean.setReservePlanId(reservePlanBean.getId());
                spotPlanService.saveEntity(spotPlanBean);
                super.onSubmit(target, form);
                feedbackPanel.info("现场预案保存成功！");
                target.add(feedbackPanel);
                target.add(table);
                spotPlanForm.setModelObject(new SpotPlanBean());
                target.add(spotPlanForm);
            }
        };
        spotPlanForm.add(ajaxSubmitLinkCreate);
        spotPlanForm.add(new AjaxButton("cancel") {
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

    private void addDeleteLink(Item<SpotPlanBean> item, String linkName, final SpotPlanBean spotPlanBean, final WebMarkupContainer table) {
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
                spotPlanService.deleteEntity(spotPlanBean.getId());
                target.add(table);
            }
        };
        item.add(ajaxLink.setOutputMarkupId(true));
    }

    protected void onDeleteTabs(AjaxRequestTarget target) {
    }

    //通过字典初始化下拉列表
    private void initSelect(String name, String dictConst) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, dictConst);
        spotPlanForm.add(listSites);
    }

    private void addSelectToForm() {
        initSelect("type", IDictConstService.ACCIDENT_TYPE);
    }

    class SpotPlanDataProvider extends ListDataProvider<SpotPlanBean> {
        private SpotPlanBean spotPlanBean = null;

        public void setSpotPlanBean(ReservePlanBean reservePlanBean) {
            if (null != reservePlanBean) {
                this.spotPlanBean = new SpotPlanBean();
                this.spotPlanBean.setReservePlanId(reservePlanBean.getId());
            }
        }

        @Override
        protected List<SpotPlanBean> getData() {
            if (spotPlanBean == null) {
                return new ArrayList<>();
            } else {
                return spotPlanService.queryByReservePlanId(spotPlanBean);
            }
        }
    }
}
