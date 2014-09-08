package com.daren.example.webapp.wicket.activiti.components.dynamic;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.resource.IResourceStream;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ActivitiTaskFormPanel extends AbstractDynamicActivitiPanel {
    @Inject
    private transient FormService formService;
    @Inject
    private transient TaskService taskService;
    @Inject
    private transient RepositoryService repositoryService;

    public ActivitiTaskFormPanel(final String id, final IModel<Task> model) {
        super(id, model);
        setOutputMarkupId(true);
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("form", new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>()));
        form.setOutputMarkupId(true);
        add(form);
        final Task task = model.getObject();
        form.add(new AjaxFallbackButton("submit", new ResourceModel("submit"), form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                taskService.claim(task.getId(), "test");
//                taskService.claim(model.getObject().getId(), ((ActivitiAuthenticatedWebSession) WebSession.get()).getUser().getId());
                Map<String, String> submitMap=new HashMap<String, String>();
                for (Map.Entry<String, Object> entry:((Map<String, Object>)form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                formService.submitTaskFormData(task.getId(), submitMap);
                model.setObject(null);
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        fillForm(form, taskFormData.getFormProperties());
    }

    public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
        //don't cache for now
        return null;
    }

    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(((Task) getDefaultModelObject()).getProcessDefinitionId()).singleResult();
        String id = ((Task) getDefaultModelObject()).getId();
        return createIResourceStreamFromInputStream(repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), "OSGI-INF/activiti/"+formService.getTaskFormData(id).getFormKey()));

    }
}