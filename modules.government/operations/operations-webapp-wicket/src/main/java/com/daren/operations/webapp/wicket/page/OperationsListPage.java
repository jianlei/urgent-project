package com.daren.operations.webapp.wicket.page;

import com.daren.attachment.webapp.wicket.page.WindowGovernmentPage;
import com.daren.core.web.component.navigator.CustomerPagingNavigator;
import com.daren.core.web.wicket.BasePanel;
import com.daren.operations.api.biz.IOperationsService;
import com.daren.operations.entities.OperationsBean;
import com.daren.operations.webapp.wicket.Const;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
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
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;

import javax.inject.Inject;
import java.util.List;


/**
 * @类描述：特种作业人员操作资格证
 * @创建人：王凯冉
 * @创建时间：2014-08-01 上午10:25
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class OperationsListPage extends BasePanel {
    final WebMarkupContainer dialogWrapper;
    WindowGovernmentPage dialog;
    OperationsDataProvider provider = new OperationsDataProvider();
    @Inject
    private IOperationsService operationsService;
    @Inject
    private transient IdentityService identityService;
    @Inject
    private transient RuntimeService runtimeService;
    @Inject
    private TaskService taskService;
    JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedBack");

    public OperationsListPage(final String id, final WebMarkupContainer wmc) {
        super(id, wmc);
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
        this.add(feedbackPanel);
        final WebMarkupContainer table = new WebMarkupContainer("table");
        add(table.setOutputMarkupId(true));
        DataView<OperationsBean> listView = new DataView<OperationsBean>("rows", provider, 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<OperationsBean> item) {
                final OperationsBean operationsBean = item.getModelObject();
                item.add(new Label("name", operationsBean.getName()));
                item.add(new Label("phone", operationsBean.getPhone()));
                item.add(new Label("workType", operationsBean.getWorkType()));
                item.add(new Label("operationProject", operationsBean.getOperationProject()));
                item.add(new Label("enterpriseName", operationsBean.getEnterpriseName()));
                item.add(new Label("linkHandle", operationsBean.getLinkHandle()));
                item.add(getToCreatePageLink("check_name", operationsBean));
                item.add(getToUploadPageLink("upload", operationsBean));
                item.add(getDuplicateLink("duplicate", operationsBean));
                item.add(getSubmitLink("submit", operationsBean));
            }
        };
        CustomerPagingNavigator pagingNavigator = new CustomerPagingNavigator("navigator", listView);
        table.add(pagingNavigator);
        table.add(listView);
        createQuery(table, provider, id, wmc);
    }

    private AjaxButton getToCreatePageAjaxButton(String wicketId, final OperationsBean operationsBean) {
        AjaxButton ajaxLink = new AjaxButton(wicketId) {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createButtonOnClick(operationsBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getToCreatePageLink(String wicketId, final OperationsBean operationsBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createButtonOnClick(operationsBean, target);
            }
        };
        return ajaxLink;
    }

    private AjaxLink getDuplicateLink(String wicketId, final OperationsBean operationsBean){
        AjaxLink alinkDuplicate = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", operationsBean, "list");
            }
        };
        return alinkDuplicate;
    }

    private AjaxLink getSubmitLink(String wicketId, final OperationsBean operationsBean){
        AjaxLink alinkSubmit = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    String bizKey= Const.PROCESS_KEY + ":" + operationsBean.getPhone() + ":" + operationsBean.getId();
                    //获得当前登陆用户
                    identityService.setAuthenticatedUserId(operationsBean.getPhone());
                    ProcessInstance instance = runtimeService.startProcessInstanceByKey(Const.PROCESS_KEY, bizKey);
                    operationsBean.setProcessInstanceId(instance.getProcessInstanceId());
                    Task task=taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
                    operationsBean.setLinkHandle(task.getName());
                    operationsService.saveEntity(operationsBean);
                    feedbackPanel.info("特种作业人员操作资格证,启动成功！");
                }catch (Exception e){
                    feedbackPanel.info("特种作业人员操作资格证,启动失败！");
                }finally {
                    identityService.setAuthenticatedUserId(null);
                }
                target.add(feedbackPanel);
            }

        };
        return alinkSubmit;
    }

    private AjaxLink getToUploadPageLink(String wicketId, final OperationsBean operationsBean) {
        AjaxLink ajaxLink = new AjaxLink(wicketId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "上传复件", operationsBean, "upload");
            }
        };
        return ajaxLink;
    }

    protected void createButtonOnClick(OperationsBean operationsBean, AjaxRequestTarget target) {
    }

    private void createDialog(AjaxRequestTarget target, final String title, OperationsBean operationsBean, String type) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowGovernmentPage("dialog", title, operationsBean.getId(), type, "operations") {
            @Override
            public void updateTarget(AjaxRequestTarget target) {
            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    /**
     * 处理查询页面
     *
     * @param table
     */
    private void createQuery(final WebMarkupContainer table, final OperationsDataProvider provider, final String id, final WebMarkupContainer wmc) {
        //处理查询
        Form<OperationsBean> majorHazardSourceBeanForm = new Form<>("formQuery", new CompoundPropertyModel<>(new OperationsBean()));
        TextField textField = new TextField("name");
        majorHazardSourceBeanForm.add(textField.setOutputMarkupId(true));
        majorHazardSourceBeanForm.add(getToCreatePageAjaxButton("create", null));
        add(majorHazardSourceBeanForm.setOutputMarkupId(true));
        AjaxButton findButton = new AjaxButton("find", majorHazardSourceBeanForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                OperationsBean operationsBean = (OperationsBean) form.getModelObject();
                provider.setOperationsBean(operationsBean);
                target.add(table);
            }
        };
        majorHazardSourceBeanForm.add(findButton);
    }

    class OperationsDataProvider extends ListDataProvider<OperationsBean> {
        private OperationsBean operationsBean = null;
        public void setOperationsBean(OperationsBean operationsBean) {
            this.operationsBean = operationsBean;
        }
        @Override
        protected List<OperationsBean> getData() {
            if (operationsBean == null || null == operationsBean.getName() || "".equals(operationsBean.getName().trim()))
                return operationsService.getAllEntity();
            else {
                return operationsService.getAllEntity();
            }
        }
    }
}
