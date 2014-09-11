package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @类描述：危险化学品流程的审批Taskform类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageAuditTaskFormPage extends BaseFormPanel {

    @Inject
    protected IChemistryManageBeanService chemistryManageBeanService;
    ChemistryManageBean bean = new ChemistryManageBean();
    @Inject
    private transient FormService formService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private transient TaskService taskService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    private String comment;
    private boolean accepted;

    public ChemistryManageAuditTaskFormPage(String id, final IModel<Task> model) {
        super(id, model);
        setOutputMarkupId(true);

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
        bean = (ChemistryManageBean) chemistryManageBeanService.getEntity(new Long(beanId));

        final Form<ChemistryManageBean> form = new Form<>("startForm", new CompoundPropertyModel<>(bean));
        form.setOutputMarkupId(true);
        add(form);

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //add data to form
        form.add(new Label("code").setOutputMarkupId(true));
        form.add(new Label("name").setOutputMarkupId(true));
        form.add(new Label("header").setOutputMarkupId(true));
        form.add(new Label("address").setOutputMarkupId(true));
        form.add(new Label("unitType").setOutputMarkupId(true));
        form.add(new Label("scope").setOutputMarkupId(true));
        form.add(new Label("mode").setOutputMarkupId(true));
        form.add(new Label("startDate").setOutputMarkupId(true));
        form.add(new Label("endDate").setOutputMarkupId(true));
        form.add(new Label("unitsDate").setOutputMarkupId(true));
        form.add(new Label("proposerId").setOutputMarkupId(true));
        form.add(new Label("qyid").setOutputMarkupId(true));
        form.add(new Label("taskName", task.getName()));


        form.add(new CheckBox("accepted", new PropertyModel<Boolean>(this, "accepted")).setOutputMarkupId(true));

        form.add(new TextField("comment", new PropertyModel<String>(this, "comment")).setOutputMarkupId(true));

        /*StartFormData startFormData = formService.getStartFormData(task.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();*/

        //保存按钮
        form.add(new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                logger.debug("Trying to start new process for {}", task.getId());
                try {
                    //todo 封装到service

                    taskService.claim(task.getId(), currentUserName);
                    identityService.setAuthenticatedUserId(currentUserName);
                    taskService.addComment(task.getId(), processInstanceId, comment);
                    Map<String, String> submitMap = new HashMap<String, String>();
                    submitMap.put("accepted", String.valueOf(accepted));
                    formService.submitTaskFormData(task.getId(), submitMap);
                    model.setObject(null);
                    feedbackPanel.info("事项处理成功，请点击关闭按钮！");
                    this.setEnabled(false);
                    target.add(ChemistryManageAuditTaskFormPage.this.findParent(TabbedPanel.class));
                } finally {
                    identityService.setAuthenticatedUserId(null);
                }
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                feedbackPanel.error("事项处理失败");
                target.add(feedbackPanel);
            }
        });

        //关闭按钮
        form.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                TabsUtil.deleteTab(target, ChemistryManageAuditTaskFormPage.this.findParent(TabbedPanel.class));
            }
        });
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
