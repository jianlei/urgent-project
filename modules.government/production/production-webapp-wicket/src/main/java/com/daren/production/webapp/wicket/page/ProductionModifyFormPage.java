package com.daren.production.webapp.wicket.page;

import com.daren.attachment.api.biz.IAttachmentService;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.core.api.IConst;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisDeleteAjaxLink;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
import com.daren.production.webapp.wicket.Const;
import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @类描述：烟花爆竹经营许可证流程
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProductionModifyFormPage extends BaseFormPanel {
    @Inject
    protected IProductionService productionService;
    ProductionBean bean=new ProductionBean();
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private transient TaskService taskService;
    @Inject
    private IAttachmentService attachmentService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    WebMarkupContainer table;

    public ProductionModifyFormPage(String id, final IModel<Task> model) {
        super(id, model);
        setOutputMarkupId(true);
        //初始化dialogWrapper
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog", ""));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
        final Task task = model.getObject();
        //通过任务对象获取流程实例
        final String processInstanceId = task.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //通过流程实例获取“业务键”
        String businessKey = pi.getBusinessKey();
        //拆分业务键，拆分成“业务对象名称”和“业务对象ID”的数组
        String beanId = null;
        if (StringUtils.isNotBlank(businessKey)) {
            beanId = businessKey.split(":")[2];
        }
        bean = (ProductionBean) productionService.getEntity(new Long(beanId));
        final Form<ProductionBean> form = new Form<>("startForm", new CompoundPropertyModel<>(bean));
        form.setOutputMarkupId(true);
        add(form);
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //add data to form
        form.add(new TextField("name").setOutputMarkupId(true));
        form.add(new TextField("head").setOutputMarkupId(true));
        form.add(new TextField("phone").setOutputMarkupId(true));
        form.add(new TextField("address").setOutputMarkupId(true));
        form.add(new TextField("economicsType").setOutputMarkupId(true));
        form.add(new TextField("scope").setOutputMarkupId(true));
        //上传复件按钮
        final AjaxButton uploadButton = new AjaxButton("upload", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (dialog != null) {
                    dialogWrapper.removeAll();
                }
                dialog = new WindowGovernmentPage("dialog", "上传附件", bean.getId(), "upload", "production") {
                    @Override
                    public void updateTarget(AjaxRequestTarget target) {}
                };
                target.add(dialogWrapper);
                dialog.open(target);
            }
        };
        form.add(uploadButton);
        //保存按钮
        form.add(new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    //todo 需要加入到service

                    taskService.claim(task.getId(), currentUserName);
                    taskService.complete(task.getId());
                    ProductionBean bean= (ProductionBean) form.getModelObject();
                    Task taskN=taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                    bean.setLinkHandle(taskN.getName());
                    productionService.saveEntity(bean);
                    feedbackPanel.info("任务处理成功，请点击关闭按钮！");
                    this.setEnabled(false);
                    uploadButton.setEnabled(false);
                    target.add(ProductionModifyFormPage.this.findParent(TabbedPanel.class));
                } finally {
                    identityService.setAuthenticatedUserId(null);
                }
            }
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                feedbackPanel.error("任务处理失败");
                target.add(feedbackPanel);
            }
        });
        //关闭按钮
        form.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                TabsUtil.deleteTab(target, ProductionModifyFormPage.this.findParent(TabbedPanel.class));
            }
        });
        DataProvider dataProvider=new DataProvider(bean.getId(), "production");
        table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        DataView<AttachmentBean> lv = new DataView<AttachmentBean>("rows", dataProvider, 20) {
            @Override
            protected void populateItem(Item<AttachmentBean> item) {
                final AttachmentBean attachmentBean = item.getModelObject();
                item.add(new Label("names", attachmentBean.getName()));
                item.add(initPreviewButton(attachmentBean));
                item.add(initDeleteButton(attachmentBean));
            }
        };
        table.setVersioned(false);
        table.add(lv);
        form.add(table);
    }

    private class DataProvider implements IDataProvider<AttachmentBean> {
        List<AttachmentBean> beanList=new ArrayList<>();
        private long id;
        private String str;
        public  DataProvider(long id,String str){
            this.id=id;
            this.str=str;
            getData();
        }
        public List<AttachmentBean> getData() {
            beanList = attachmentService.getAttachmentBeanByParentIdAndAppType(id, str);
            return beanList;
        }
        @Override
        public Iterator<? extends AttachmentBean> iterator(long l, long l2) {
            getData();
            return beanList.iterator();
        }
        @Override
        public long size() {
            return beanList.size();
        }
        @Override
        public IModel<AttachmentBean> model(AttachmentBean attachmentBean) {
            return Model.of(attachmentBean);
        }
        @Override
        public void detach() {
        }
    }

    private AjaxLink initPreviewButton(final AttachmentBean attachmentBean) {
        AjaxLink alink = new AjaxLink("previewDuplicate") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                FileInputStream fileInputStream = null;
                try {
                    File tempFile = new File(IConst.OFFICE_WEB_PATH_TEMP + attachmentBean.getName());
                    fileInputStream = new FileInputStream(attachmentBean.getPath());
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                    fileInputStream.close();
//                    createDialog(target, "Office", IConst.OFFICE_WEB_PATH_READ + attachmentBean.getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return alink;
    }
    private AjaxLink initDeleteButton(final AttachmentBean attachmentBean) {
        IrisDeleteAjaxLink alink = new IrisDeleteAjaxLink("deleteDuplicate") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                attachmentService.deleteEntity(attachmentBean.getId());
                File file = new File(attachmentBean.getPath());
                file.delete();
                target.add(table);
            }
        };
        return alink;
    }
}
