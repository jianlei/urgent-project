package com.daren.example.webapp.wicket.activiti.components.dynamic;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
public class ActivitiStartProcessFormPanel extends AbstractDynamicActivitiPanel {

    private static final Logger LOG = LoggerFactory.getLogger(ActivitiStartProcessFormPanel.class);

    @Inject
    private transient FormService formService;

    @Inject
    private transient RepositoryService repositoryService;

    public ActivitiStartProcessFormPanel(final String id, final IModel<ProcessDefinition> model) {
        super(id, model);
        setOutputMarkupId(true);
        HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
        final Form<Map<String, Object>> form = new Form<Map<String, Object>>("form", new CompoundPropertyModel<Map<String, Object>>(stringObjectHashMap));
        form.setOutputMarkupId(true);
        add(form);
        ProcessDefinition processDefinition = model.getObject();
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        fillForm(form, formProperties);
        form.add(new AjaxFallbackButton("submit", new ResourceModel("submit"), form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                LOG.debug("Trying to start new process for {}", model.getObject().getId());
                Map<String, String> submitMap=new HashMap<String, String>();
                for (Map.Entry<String, Object> entry:((Map<String, Object>)form.getDefaultModelObject()).entrySet()) {
                    submitMap.put(entry.getKey(), entry.getValue().toString());
                }
                ProcessInstance instance = formService.submitStartFormData(model.getObject().getId(), submitMap);
                LOG.debug("Started new process for {}", model.getObject().getId());
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }

    public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
        //don't cache for now
        return null;
    }

    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {

        ProcessDefinition processDefinition= ((ProcessDefinition) getDefaultModelObject());
       // List<String> list=repositoryService.getDeploymentResourceNames(processDefinition.getDeploymentId());
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), "OSGI-INF/activiti/" + startFormData.getFormKey());
        return createIResourceStreamFromInputStream(resourceAsStream);
    }

}