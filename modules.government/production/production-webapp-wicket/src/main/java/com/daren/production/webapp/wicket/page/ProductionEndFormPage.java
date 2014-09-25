package com.daren.production.webapp.wicket.page;

import com.daren.attachment.api.biz.IAttachmentService;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.api.IConst;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.production.api.biz.IProductionService;
import com.daren.production.entities.ProductionBean;
import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.daren.workflow.webapp.wicket.util.WorkflowUtil;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @类描述：安全生产许可证(非煤矿矿山企业)
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProductionEndFormPage extends BaseFormPanel {
    @Inject
    private IAttachmentService attachmentService;
    @Inject
    private IProductionService productionService;
    @Inject
    private transient FormService formService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private transient TaskService taskService;
    ProductionBean bean = new ProductionBean();
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    private String comment="审批通过";
    private String accepted="同意";

    public ProductionEndFormPage(String id, final IModel<Task> model) {
        super(id, model);
        setOutputMarkupId(true);
        //得到任务对象
        final Task task = model.getObject();
        //通过任务对象获取流程实例
        final String processInstanceId = task.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //通过流程实例获取“业务键”
        String businessKey = pi.getBusinessKey();
        //拆分业务键，拆分成“业务对象名称”和“业务对象ID”的数组
        String beanId = WorkflowUtil.getBizId(businessKey);
        //得到业务实体
        bean = (ProductionBean) productionService.getEntity(new Long(beanId));
        final Form<ProductionBean> form = new Form<>("startForm", new CompoundPropertyModel<ProductionBean>(bean));
        form.setOutputMarkupId(true);
        add(form);
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //设置页面字段
        form.add(new Label("name",new PropertyModel<String>(bean, "name")));
        form.add(new Label("head",new PropertyModel<String>(bean, "head")));
        form.add(new Label("phone",new PropertyModel<String>(bean, "phone")));
        form.add(new Label("address",new PropertyModel<String>(bean, "address")));
        form.add(new Label("economicsType",new PropertyModel<String>(bean, "economicsType")));
        form.add(new Label("scope",new PropertyModel<String>(bean, "scope")));
        form.add(new TextField("code",new PropertyModel<String>(bean, "code")));
        form.add(new TextField("card",new PropertyModel<String>(bean, "card")));
        //日期控件//
        final DatePicker unitsDateTime = new DatePicker("unitsDate", "yyyy-MM-dd", new Options("dateFormat", Options.asString("yy-mm-dd")));
        form.add(unitsDateTime);
        //日期控件//
        final DatePicker awardDateTime = new DatePicker("awardDate", "yyyy-MM-dd", new Options("dateFormat", Options.asString("yy-mm-dd")));
        form.add(awardDateTime);
        //日期控件//
        final DatePicker effectiveDateTime = new DatePicker("effectiveDate", "yyyy-MM-dd", new Options("dateFormat", Options.asString("yy-mm-dd")));
        form.add(effectiveDateTime);

        //保存按钮
        form.add(new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    //todo 封装到service
                    taskService.claim(task.getId(), currentUserLoginName);
                    //添加备注信息
                    identityService.setAuthenticatedUserId(currentUserLoginName);
                    taskService.addComment(task.getId(), processInstanceId, comment);
                    taskService.complete(task.getId());
                    model.setObject(null);
                    ProductionBean productionBean = (ProductionBean) form.getModelObject();
                    productionBean.setLinkHandle("审批结束");
                    productionService.saveEntity(productionBean);
                    feedbackPanel.info("任务处理成功，请点击关闭按钮！");
                    this.setEnabled(false);
                    target.add(ProductionEndFormPage.this.findParent(TabbedPanel.class));
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
                TabsUtil.deleteTab(target, ProductionEndFormPage.this.findParent(TabbedPanel.class));
            }
        });

        List<AttachmentBean> list = attachmentService.getAttachmentBeanByParentIdAndAppType(bean.getId(), "fireworks");
        WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        PageableListView<AttachmentBean> lv = new PageableListView<AttachmentBean>("rows", list, 20) {
            @Override
            protected void populateItem(ListItem<AttachmentBean> item) {
                final AttachmentBean attachmentBean = item.getModelObject();
                item.add(new Label("names", attachmentBean.getName()));
                item.add(initPreviewButton(attachmentBean));
            }
        };
        table.setVersioned(false);
        table.add(lv);
        form.add(table);
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
