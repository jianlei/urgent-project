package com.daren.government.webapp.wicket.page;

import com.daren.admin.entities.UserBean;
import com.daren.core.util.DateUtil;
import com.daren.government.webapp.wicket.model.TasksAssignableToUserModel;
import com.daren.government.webapp.wicket.model.UserIdModel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
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

    public TaskListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc,CONST_LIST);
    }

    @Override
    public Fragment getMainFragment(String panelId, String makeupId) {
        return new MainFragment(panelId,makeupId);
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
        private IndicatingAjaxLink initStartButton(Task processDefinition) {
            return new IndicatingAjaxLink("start") {
                @Override
                public void onClick(AjaxRequestTarget target) {

                }
            };
        }

        /**
         * 任务查看按钮
         *
         * @return
         */
        private IndicatingAjaxLink initViewButton(Task processDefinition) {
            return new IndicatingAjaxLink("view") {
                @Override
                public void onClick(AjaxRequestTarget target) {

                }
            };
        }
    }}
