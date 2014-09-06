package com.daren.government.webapp.wicket.page;

import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.wicket.ValidationStyleBehavior;
import com.daren.government.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
 * @类描述：demo流程的启动form类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DemoStartFormPage extends Panel {
    private static final Logger LOG = LoggerFactory.getLogger(DemoStartFormPage.class);
    @Inject
    private transient FormService formService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public DemoStartFormPage(String id, final IModel<ProcessDefinition> model) {
        super(id, model);
        setOutputMarkupId(true);
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("startForm", new CompoundPropertyModel<Map<String, Object>>(stringObjectHashMap));
        form.setOutputMarkupId(true);
        add(form);

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));

        final ProcessDefinition processDefinition = model.getObject();
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();

        form.add(new TextField("title").setOutputMarkupId(true).add(new ValidationStyleBehavior()));
        form.add(new TextField("description").setOutputMarkupId(true).add(new ValidationStyleBehavior()));

        form.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                TabsUtil.deleteTab(target,DemoStartFormPage.this.findParent(TabbedPanel.class));
            }
        });

        form.add(new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                LOG.debug("Trying to start new process for {}",processDefinition.getId());
                Map<String, String> submitMap=new HashMap<String, String>();
                for (Map.Entry<String, Object> entry:((Map<String, Object>)form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                ProcessInstance instance = formService.submitStartFormData(processDefinition.getId(), submitMap);
                LOG.debug("Started new process for {}", processDefinition.getId());
                feedbackPanel.info("流程启动成功！");
                target.add(feedbackPanel);
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                feedbackPanel.error("流程启动失败");
                target.add(feedbackPanel);
            }
        });
    }
}
