package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.workflow.webapp.wicket.page.BaseFormPanel;
import com.daren.workflow.webapp.wicket.util.TabsUtil;
import com.daren.workflow.webapp.wicket.util.WorkflowUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    private String comment="审批通过";
    private String accepted="同意";

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
        String beanId = WorkflowUtil.getBizId(businessKey);

        bean = (ChemistryManageBean) chemistryManageBeanService.getEntity(new Long(beanId));

        final Form<Map<String, String>> form = new Form<>("startForm", new CompoundPropertyModel<Map<String, String>>(new HashMap<String, String>()));
        form.setOutputMarkupId(true);
        add(form);

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //add data to form
        form.add(new Label("code",new PropertyModel<String>(bean, "code")));
        form.add(new Label("name",new PropertyModel<String>(bean, "name")));
        form.add(new Label("header",new PropertyModel<String>(bean, "header")));
        form.add(new Label("address",new PropertyModel<String>(bean, "address")));
        form.add(new Label("unitType",new PropertyModel<String>(bean, "unitType")));
        form.add(new Label("scope",new PropertyModel<String>(bean, "scope")));
        form.add(new Label("mode",new PropertyModel<String>(bean, "mode")));
        form.add(new Label("startDate",new PropertyModel<String>(bean, "startDate")));
        form.add(new Label("endDate",new PropertyModel<String>(bean, "endDate")));
        form.add(new Label("unitsDate",new PropertyModel<String>(bean, "unitsDate")));
        form.add(new Label("proposerId",new PropertyModel<String>(bean, "proposerId")));
        form.add(new Label("qyid",new PropertyModel<String>(bean, "qyid")));
        form.add(new Label("taskName", task.getName()));

        //审批结果
        final List<String> TYPES = Arrays.asList(new String[]{"同意", "不同意"});
        RadioChoice<String> radio_accepted = new RadioChoice<String>(
                "accepted", new PropertyModel<String>(this, "accepted"), TYPES);
        form.add(radio_accepted);
        //审批意见
        form.add(new TextField("comment", new PropertyModel<String>(this, "comment")).setOutputMarkupId(true));

        TaskFormData startFormData = formService.getTaskFormData(task.getId());
        final List<FormProperty> formProperties = startFormData.getFormProperties();

        //保存按钮
        form.add(new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                logger.debug("Trying to finish a task for {}", task.getId());
                try {
                    //todo 封装到service
                    taskService.claim(task.getId(), currentUserName);
                    //添加备注信息
                    identityService.setAuthenticatedUserId(currentUserName);
                    taskService.addComment(task.getId(), processInstanceId, comment);

                    Map<String, String> submitMap = new HashMap<String, String>();
                    boolean passed=accepted.equals("同意")?true:false;
                    submitMap.put("accepted", String.valueOf(passed));
                    taskService.setVariablesLocal(task.getId(), submitMap);

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

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
