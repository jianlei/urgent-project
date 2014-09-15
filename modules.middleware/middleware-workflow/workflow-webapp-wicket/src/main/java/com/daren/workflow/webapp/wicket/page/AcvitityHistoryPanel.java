package com.daren.workflow.webapp.wicket.page;

import com.daren.core.util.DateUtil;
import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.core.web.wicket.manager.BizDataPanelManager;
import com.daren.workflow.webapp.wicket.util.WorkflowUtil;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import javax.inject.Inject;
import java.util.List;

/**
 * @类描述：流程活动历史页面类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AcvitityHistoryPanel extends Panel {
    @Inject
    private transient HistoryService historyService;
    @Inject
    private transient RepositoryService repositoryService;

    @Inject
    private transient FormService formService;

    @Inject
    private transient TaskService taskService;


    public AcvitityHistoryPanel(String id, String historyProcessId, String processDefinitionId) {
        super(id);
        HistoricProcessInstance processInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(historyProcessId).singleResult();

        String processKey=WorkflowUtil.getProcessKey(processDefinitionId);
        IBizDataHandler handler= BizDataPanelManager.getInstall().findPanelByKey(processKey);

        String beanId=WorkflowUtil.getBizId(processInstance.getBusinessKey());
        initBizData(beanId,handler);
        initDataTable(historyProcessId, processDefinitionId);
        initProcessView(historyProcessId, processDefinitionId);
    }

    /**
     * 业务数据显示
     * @param beanId
     * @param handler
     */
    public void initBizData(String beanId,IBizDataHandler handler){
        Panel bizPanel=handler.getPanel("bizData",beanId);
        this.add(bizPanel);
    }

    public void initDataTable(String historyProcessId, String processDefinitionId) {

        /*final WebMarkupContainer table = new WebMarkupContainer("varTable");

        final DataView<HistoricVariableInstance> listView = new DataView<HistoricVariableInstance>("rows", new HistoricVariableInstanceProvider(historyProcessId), 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<HistoricVariableInstance> item) {
                final HistoricVariableInstance historicVariableInstance = item.getModelObject();
                item.add(new Label("varName", historicVariableInstance.getVariableName()));
                item.add(new Label("varValue", historicVariableInstance.getValue().toString()));
            }
        };
        table.add(listView);
        this.add(table.setOutputMarkupId(true));*/

    }

    private void initProcessView(String historyProcessId, String processDefinitionId) {
        final WebMarkupContainer table = new WebMarkupContainer("table");

        final DataView<HistoricActivityInstance> listView = new DataView<HistoricActivityInstance>("rows", new HistoricActivityInstanceProvider(historyProcessId), 10) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(Item<HistoricActivityInstance> item) {
                final HistoricActivityInstance historicActivityInstance = item.getModelObject();
                item.add(new Label("id", historicActivityInstance.getId()));
                item.add(new Label("name", historicActivityInstance.getActivityName()));
                item.add(new Label("assignee", historicActivityInstance.getAssignee()));
                item.add(new Label("startTime", DateUtil.convertDateToString(historicActivityInstance.getStartTime(), DateUtil.longSdf)));
                item.add(new Label("endTime", DateUtil.convertDateToString(historicActivityInstance.getEndTime(), DateUtil.longSdf)));
                List<Comment> commentList= taskService.getTaskComments(historicActivityInstance.getTaskId());
                String str="";
                for(Comment comment:commentList){
                    str=comment.getFullMessage()+str+" ";
                }
                item.add(new Label("comment", str));
            }
        };
        table.add(listView);
        this.add(table.setOutputMarkupId(true));
    }

    class HistoricVariableInstanceProvider extends ListDataProvider {

        String historyProcessId;

        public HistoricVariableInstanceProvider(String historyProcessId) {
            this.historyProcessId = historyProcessId;
        }

        @Override
        protected List<HistoricVariableInstance> getData() {
            List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(historyProcessId).list();
            for (int i = list.size(); i > 0; i--) {
                if (null == list.get(i - 1).getTaskId()) {
                    list.remove(i - 1);
                }
            }
            return list;
        }
    }

    class HistoricActivityInstanceProvider extends ListDataProvider {

        String historyProcessId;

        public HistoricActivityInstanceProvider(String historyProcessId) {
            this.historyProcessId = historyProcessId;
        }

        @Override
        protected List<HistoricActivityInstance> getData() {
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historyProcessId).list();
            for (int i = list.size(); i > 0; i--) {
                if (null == list.get(i - 1).getTaskId()) {
                    list.remove(i - 1);
                }
            }
            return list;
        }
    }
}
