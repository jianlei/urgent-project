package com.daren.workflow.webapp.wicket.page;

import com.daren.admin.entities.UserBean;
import com.daren.core.util.DateUtil;
import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.core.web.wicket.manager.FormPanelManager;
import com.daren.workflow.webapp.wicket.model.TaskModel;
import com.daren.workflow.webapp.wicket.model.TasksAssignableToUserModel;
import com.daren.workflow.webapp.wicket.model.UserIdModel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：待办事项列表页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class TaskListPage extends WorkflowBasePanel{
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "待办事项";
    //dialog定义  -dlw
    final WebMarkupContainer dialogWrapper;
    private WorkFlowImgViewDialog dialog;

    @Inject
    private transient RepositoryService repositoryService;

    @Inject
    private transient FormService formService;

    public TaskListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc,CONST_LIST);
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog"));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
    }

    @Override
    public Fragment getMainFragment(String panelId, String makeupId) {
        return new MainFragment(panelId,makeupId);
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param tabTitle "修改字典"||"新增字典"
     * @param model        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String tabTitle, final IFormHandler formHandler, final IModel model) {
        //去掉第二个tab
        if (tabPanel.getModelObject().size() == 2) {
            tabPanel.getModelObject().remove(1);
        }
        tabPanel.add(new AjaxTab(Model.of(tabTitle)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getLazyPanel(String panelId) {
                //通过repeatingView增加新的panel
                repeatingView.removeAll();
                repeatingView.add(formHandler.getPanel(repeatingView.newChildId(),model ) );
                Fragment fragment = new Fragment(panelId, "addPanel", TaskListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });
        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;
        public MainFragment(String id, String markupId) {
            super(id, markupId, TaskListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));

            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));
            final IModel<List<Task>> taskList=new TasksAssignableToUserModel(new UserIdModel());
            //add listview
            final ListView<Task> listView = new ListView<Task>("rows", taskList) {
                private static final long serialVersionUID = 1L;
                @Override
                protected void populateItem(ListItem<Task> item) {
                    final Task row = item.getModelObject();
                    item.add(new Label("id", row.getId()));
                    item.add(new Label("name", row.getName()));
                    item.add(new Label("description", row.getDescription()));
                    item.add(new Label("owner", row.getOwner()));
                    item.add(new Label("assignee", row.getAssignee()));
                    item.add(new Label("createTime", DateUtil.convertDateToString(row.getCreateTime(), DateUtil.longSdf)));
                    /*item.add(new ActiveActivityImage("image",row));//生成工作流图片*/
                    item.add(initStartButton(row));
                    item.add(initViewButton(row));
                }
            };
            table.add(listView);


            //search form
            Form<UserBean> userForm = new Form<>("form", new CompoundPropertyModel<>(new UserBean()));
            TextField textField = new TextField("name");
            userForm.add(textField.setOutputMarkupId(true));

//            userForm.add(initStartButton());
            add(userForm);

        }
        /**
         * 任务处理按钮
         *
         * @return
         */
        private IndicatingAjaxLink initStartButton(final Task task) {
            return new IndicatingAjaxLink("start") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    //获得流程定义
                    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
                    //获得任务form数据
                    TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                    IFormHandler formHandler= FormPanelManager.getInstall().findFormByKey(processDefinition.getKey(), taskFormData.getFormKey());
                    createNewTab(target, "启动 " + processDefinition.getName(), formHandler,new TaskModel(task.getId()));
                }
            };
        }
        /**
         * 任务查看按钮
         *
         * @return
         */
        private IndicatingAjaxLink initViewButton(final Task processDefinition) {
            return new IndicatingAjaxLink("view") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createDialog(target, "流程查看",processDefinition);//dialog函数调用  --dlw
                }
            };
        }

        //创建文本框 ---dlw
        private void createDialog(AjaxRequestTarget target, final String title, final Task processDefinition) {
            if (dialog != null) {
                dialogWrapper.removeAll();
            }
            dialog = new WorkFlowImgViewDialog("dialog", title,processDefinition) {//new 窗体中内容 例如 new WindowMapPage对应WindowMapPage.hml
                @Override
                public void updateTarget(AjaxRequestTarget target) {

                }
            };
            target.add(dialogWrapper);
            dialog.open(target);
        }
    }

}
