package com.daren.workflow.web.bootup.wicket;

import com.daren.core.web.component.form.IrisDropDownChoice;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 14-9-12.
 */
public class Apply extends WebPage {

    @Inject
    private transient RepositoryService repositoryService;

    String phone;
    String selected;

    public Apply() {
        super();
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);
        form.setOutputMarkupId(true);

        final TextField nameText = new TextField("phone");

        form.add(nameText);

        AjaxButton ajaxSubmitLink = new AjaxButton("save", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Apply apply = (Apply) form.getModelObject();
                System.out.println("123");
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                target.add(feedbackPanel);
            }
        };
        form.add(ajaxSubmitLink);

        initSelect(form);
    }

    //通过Map初始化下拉列表
    private void initSelect(String name, Map<String, String> typeMap, Form form) {
        //下拉列表
        IrisDropDownChoice<String> listSites = new IrisDropDownChoice<String>(name, typeMap);
        form.add(listSites);
    }

    private void initSelect(Form form) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        Map<String, String> typeMap = new HashMap<String, String>();
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ProcessDefinition processDefinition = list.get(i);
                typeMap.put(processDefinition.getName(), processDefinition.getName());
            }
        }

        initSelect("selected", typeMap, form);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
