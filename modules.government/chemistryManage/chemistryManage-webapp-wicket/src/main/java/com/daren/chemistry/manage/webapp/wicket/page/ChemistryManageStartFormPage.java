package com.daren.chemistry.manage.webapp.wicket.page;

import com.daren.chemistry.manage.api.biz.IChemistryManageBeanService;
import com.daren.chemistry.manage.entities.ChemistryManageBean;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.government.webapp.wicket.page.BaseFormPanel;
import com.daren.government.webapp.wicket.util.TabsUtil;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：危险化学品流程的启动form类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageStartFormPage extends BaseFormPanel {

    @Inject
    protected IChemistryManageBeanService chemistryManageBeanService;
    ChemistryManageBean bean=new ChemistryManageBean();
    @Inject
    private transient FormService formService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    private JQueryFeedbackPanel feedbackPanel; //信息显示

    public ChemistryManageStartFormPage(String id, final IModel<ProcessDefinition> model) {
        super(id, model);
        setOutputMarkupId(true);

        final Form<ChemistryManageBean> form = new Form<>("startForm", new CompoundPropertyModel<>(bean));
        form.setOutputMarkupId(true);
        add(form);

        feedbackPanel = new JQueryFeedbackPanel("feedback");
        form.add(feedbackPanel.setOutputMarkupId(true));
        //add data to form
        form.add(new TextField("code").setOutputMarkupId(true));
        form.add(new TextField("name").setOutputMarkupId(true));
        form.add(new TextField("header").setOutputMarkupId(true));
        form.add(new TextField("address").setOutputMarkupId(true));
        form.add(new TextField("unitType").setOutputMarkupId(true));
        form.add(new TextField("scope").setOutputMarkupId(true));
        form.add(new TextField("mode").setOutputMarkupId(true));
        form.add(new TextField("startDate").setOutputMarkupId(true));
        form.add(new TextField("endDate").setOutputMarkupId(true));
        form.add(new TextField("unitsDate").setOutputMarkupId(true));
        form.add(new TextField("proposerId").setOutputMarkupId(true));
        form.add(new TextField("qyid").setOutputMarkupId(true));

        final ProcessDefinition processDefinition = model.getObject();
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();

        //保存按钮
        form.add(new AjaxButton("save", form) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                logger.debug("Trying to start new process for {}", processDefinition.getId());
                try {
                    //todo 需要加入到service
                    ChemistryManageBean bean= (ChemistryManageBean) form.getModelObject();
                    chemistryManageBeanService.saveEntity(bean);
                    String bizKey=processDefinition.getKey()+":"+bean.getQyid()+":"+bean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(currentUserName);
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinition.getKey(),bizKey);
                    bean.setProcessInstanceId(instance.getProcessInstanceId());
                    chemistryManageBeanService.saveEntity(bean);
                    logger.debug("Started new process for {}", processDefinition.getId());
                    feedbackPanel.info( processDefinition.getName() + "启动成功,请点击关闭按钮！");
                    this.setEnabled(false);
                    target.add(ChemistryManageStartFormPage.this.findParent(TabbedPanel.class));
                } finally {
                    identityService.setAuthenticatedUserId(null);
                }
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {
                feedbackPanel.error("流程启动失败");
                target.add(feedbackPanel);
            }
        });

        //关闭按钮
        form.add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                TabsUtil.deleteTab(target, ChemistryManageStartFormPage.this.findParent(TabbedPanel.class));
            }
        });
    }
}
