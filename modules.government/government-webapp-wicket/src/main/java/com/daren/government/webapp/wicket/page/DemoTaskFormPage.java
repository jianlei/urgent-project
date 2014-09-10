package com.daren.government.webapp.wicket.page;

import com.daren.admin.entities.UserBean;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.wicket.PermissionConstant;
import com.daren.government.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类描述：demo事项完成表单类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/7
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DemoTaskFormPage extends Panel {
    private static final Logger LOG = LoggerFactory.getLogger(DemoStartFormPage.class);
    @Inject
    private transient TaskService taskService;
    @Inject
    private transient FormService formService;

    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public DemoTaskFormPage(String id, final IModel<Task> model) {
        super(id,model);

        setOutputMarkupId(true);
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("taskForm", new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>()));
        form.setOutputMarkupId(true);
        add(form);
        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));

        final Task task = model.getObject();
        final AjaxButton save = new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                //todo 修改签收人为当前登陆用户
                Session session = SecurityUtils.getSubject().getSession();
                UserBean userBean = (UserBean) session.getAttribute(PermissionConstant.SYS_CURRENT_USER);
                taskService.claim(task.getId(), userBean.getName());
//                taskService.claim(model.getObject().getId(), ((ActivitiAuthenticatedWebSession) WebSession.get()).getUser().getId());
                Map<String, String> submitMap = new HashMap<String, String>();
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                taskService.setVariablesLocal(task.getId(), submitMap);
                formService.submitTaskFormData(task.getId(), new HashMap<String, String>());
                model.setObject(null);
                feedbackPanel.info("事项处理成功，请点击关闭按钮！");
                this.setEnabled(false);
                target.add(DemoTaskFormPage.this.findParent(TabbedPanel.class));
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                feedbackPanel.error("事项处理失败");
                target.add(feedbackPanel);
            }
        };
        form.add(save);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormProperty> formPropertyList=taskFormData.getFormProperties();
        form.add(new CheckBox("approve"));
        form.add(new TextField("justification"));
        String value="";
        for(FormProperty formProperty:formPropertyList){
            if(formProperty.getId().equals("title"))
                value=formProperty.getValue();
        }
        form.add(new Label("title", value));

        form.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                TabsUtil.deleteTab(target, DemoTaskFormPage.this.findParent(TabbedPanel.class));
            }
        });

    }
}
