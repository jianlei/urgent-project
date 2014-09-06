package com.daren.government.webapp.wicket.page;

import com.daren.admin.entities.UserBean;
import com.daren.core.util.DateUtil;
import com.daren.government.webapp.wicket.model.ProcessesHistoryListModel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.activiti.engine.history.HistoricProcessInstance;
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

import java.util.Date;
import java.util.List;

/**
 * @类描述：流程历史列表页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class HistoryListPage extends WorkflowBasePanel{
    private final static int numPerPage = 10;
    private final static String CONST_LIST = "流程历史";

    public HistoryListPage(String id, WebMarkupContainer wmc) {
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
            super(id, markupId, HistoryListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));

            //add table
            final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));
            final IModel<List<HistoricProcessInstance>> listOfProcesses=new ProcessesHistoryListModel();
            //add listview
            final ListView<HistoricProcessInstance> listView = new ListView<HistoricProcessInstance>("rows", listOfProcesses) {
                private static final long serialVersionUID = 1L;
                @Override
                protected void populateItem(ListItem<HistoricProcessInstance> item) {
                    final HistoricProcessInstance row = item.getModelObject();
                    item.add(new Label("id", row.getId()));
                    item.add(new Label("processDefinitionId", row.getProcessDefinitionId()));
                    item.add(new Label("startUserId", row.getStartUserId()));
                    item.add(new Label("startTime", DateUtil.convertDateToString(row.getStartTime(), DateUtil.longSdf)));
                    Date endTime = row.getEndTime();
                    item.add(new Label("endTime", DateUtil.convertDateToString(endTime, DateUtil.longSdf)));
                    if(null==endTime){
                        item.add(new Label("status", "办理中"));
                    }
                    else
                    {
                        item.add(new Label("status", "结束"));
                    }
//                    item.add(new Label("durationInMillis", row.getDurationInMillis()));
                    item.add(initViewButton(row));
                }
            };
            table.add(listView);


            //search form
            Form<UserBean> userForm = new Form<>("form", new CompoundPropertyModel<>(new UserBean()));
            TextField textField = new TextField("name");
            userForm.add(textField.setOutputMarkupId(true));

//            userForm.add(initViewButton());
            add(userForm);

        }
        /**
         * 初始化新增按钮
         *
         * @return
         */
        private IndicatingAjaxLink initViewButton(HistoricProcessInstance processDefinition) {
            return new IndicatingAjaxLink("view") {
                @Override
                public void onClick(AjaxRequestTarget target) {

                }
            };
        }
    }
}
