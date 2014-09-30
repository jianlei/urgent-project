package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.apply.webapp.wicket.util.PageUtil;
import com.daren.attachment.api.biz.IAttachmentService;
import com.daren.attachment.entities.AttachmentBean;
import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.api.IConst;
import com.daren.core.util.DateUtil;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisDeleteAjaxLink;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
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
import org.apache.wicket.markup.repeater.data.ListDataProvider;
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
 * @类描述：危险化学品流程的修改form类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageModifyFormPage extends BaseFormPanel {
    @Inject
    protected IChemistryManageBeanService chemistryManageBeanService;
    ChemistryManageBean bean=new ChemistryManageBean();
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private transient TaskService taskService;
    @Inject
    private transient HistoryService historyService;
    @Inject
    private IAttachmentService attachmentService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    WebMarkupContainer table;

    public ChemistryManageModifyFormPage(String id, final IModel<Task> model) {
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

        //流程例史
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        initProcessView(historicProcessInstance.getId());
        //页面表单
        final Form<ChemistryManageBean> form = getComponents(beanId);
        //上传复件按钮
        final AjaxButton uploadButton = new AjaxButton("upload", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (dialog != null) {
                    dialogWrapper.removeAll();
                }
                dialog = new WindowGovernmentPage("dialog", "上传附件", bean.getId(), "upload", "chemistryManage") {
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
                    taskService.claim(task.getId(), currentUserLoginName);
                    taskService.complete(task.getId());
                    ChemistryManageBean bean= (ChemistryManageBean) form.getModelObject();
                    Task taskN=taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                    bean.setLinkHandle(taskN.getName());
                    chemistryManageBeanService.saveEntity(bean);
                    feedbackPanel.info("任务处理成功，请点击关闭按钮！");
                    this.setEnabled(false);
                    uploadButton.setEnabled(false);
                    target.add(ChemistryManageModifyFormPage.this.findParent(TabbedPanel.class));
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
                TabsUtil.deleteTab(target, ChemistryManageModifyFormPage.this.findParent(TabbedPanel.class));
            }
        });
        DataProvider dataProvider=new DataProvider(bean.getId(), "chemistryManage");
        table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        //构造数据
        DataView<AttachmentBean> lv = new DataView<AttachmentBean>("rows", dataProvider, 20) {
            @Override
            protected void populateItem(Item<AttachmentBean> item) {
                final AttachmentBean attachmentBean = item.getModelObject();
                item.add(new Label("names", attachmentBean.getName()));
                item.add(PageUtil.initPreviewButton(attachmentBean));
                item.add(initDeleteButton(attachmentBean));
            }
        };
        table.setVersioned(false);
        table.add(lv);
        form.add(table);
    }
    //初始化页面Form表单
    private Form<ChemistryManageBean> getComponents(String beanId) {
        bean = (ChemistryManageBean) chemistryManageBeanService.getEntity(new Long(beanId));
        final Form<ChemistryManageBean> form = new Form<>("startForm", new CompoundPropertyModel<>(bean));
        form.setOutputMarkupId(true);
        add(form);
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //add data to form
        form.add(new TextField("qyCode").setOutputMarkupId(true));
        form.add(new TextField("qyName").setOutputMarkupId(true));
        form.add(new TextField("address").setOutputMarkupId(true));
        form.add(new TextField("phone").setOutputMarkupId(true));
        form.add(new TextField("fax").setOutputMarkupId(true));
        form.add(new TextField("zipCode").setOutputMarkupId(true));
        form.add(new TextField("qyType").setOutputMarkupId(true));
        form.add(new TextField("illegalPerson").setOutputMarkupId(true));
        form.add(new TextField("specialType").setOutputMarkupId(true));
        form.add(new TextField("economicsNature").setOutputMarkupId(true));
        form.add(new TextField("directorUnits").setOutputMarkupId(true));
        form.add(new TextField("registrationAuthority").setOutputMarkupId(true));
        form.add(new TextField("mainHead").setOutputMarkupId(true));
        form.add(new TextField("mainHeadId").setOutputMarkupId(true));
        form.add(new TextField("chargeHead").setOutputMarkupId(true));
        form.add(new TextField("chargeHeadId").setOutputMarkupId(true));
        form.add(new TextField("workersNumber").setOutputMarkupId(true));
        form.add(new TextField("technologyNumber").setOutputMarkupId(true));
        form.add(new TextField("safetyNumber").setOutputMarkupId(true));
        form.add(new TextField("registrationCapital").setOutputMarkupId(true));
        form.add(new TextField("fixedAssets").setOutputMarkupId(true));
        form.add(new TextField("yearSale").setOutputMarkupId(true));
        form.add(new TextField("manageAddress").setOutputMarkupId(true));
        form.add(new TextField("manageProperty").setOutputMarkupId(true));
        form.add(new TextField("storageAddress").setOutputMarkupId(true));
        form.add(new TextField("storageProperty").setOutputMarkupId(true));
        form.add(new TextField("buildingStructure").setOutputMarkupId(true));
        form.add(new TextField("storageCapacity").setOutputMarkupId(true));
        form.add(new TextField("systemName").setOutputMarkupId(true));
        form.add(new TextField("communication").setOutputMarkupId(true));
        form.add(new TextField("mode").setOutputMarkupId(true));
        form.add(new TextField("unitType").setOutputMarkupId(true));
        form.add(new TextField("scope").setOutputMarkupId(true));
        return form;
    }
    //流程审批例史
    private void initProcessView(String historyProcessId) {
        final WebMarkupContainer table = new WebMarkupContainer("historicTable");
        final DataView<HistoricActivityInstance> listView = new DataView<HistoricActivityInstance>("historicRows", new HistoricActivityInstanceProvider(historyProcessId), 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<HistoricActivityInstance> item) {
                final HistoricActivityInstance historicActivityInstance = item.getModelObject();
                item.add(new Label("id", historicActivityInstance.getId()));
                item.add(new Label("name", historicActivityInstance.getActivityName()));
                item.add(new Label("assignee", historicActivityInstance.getAssignee()));
                item.add(new Label("startTime", DateUtil.convertDateToString(historicActivityInstance.getStartTime(), DateUtil.longSdf)));
                item.add(new Label("endTime", DateUtil.convertDateToString(historicActivityInstance.getEndTime(), DateUtil.longSdf)));
                List<Comment> commentList= taskService.getTaskComments(historicActivityInstance.getTaskId());
                String str="";
                for(Comment comment:commentList){
                    str=comment.getFullMessage()+str+" ";
                }
                item.add(new Label("comment", str));
            }
        };
        table.add(listView);
        this.add(table.setOutputMarkupId(true));
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

    class HistoricActivityInstanceProvider extends ListDataProvider {
        String historyProcessId;
        public HistoricActivityInstanceProvider(String historyProcessId) {
            this.historyProcessId = historyProcessId;
        }

        @Override
        protected List<HistoricActivityInstance> getData() {
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historyProcessId).list();
            for (int i = list.size(); i > 0; i--) {
                if (null == list.get(i - 1).getTaskId()) {
                    list.remove(i - 1);
                }
            }
            return list;
        }
    }
}
