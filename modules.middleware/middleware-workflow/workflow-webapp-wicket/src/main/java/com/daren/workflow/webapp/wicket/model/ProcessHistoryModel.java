package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @类描述：流程历史模型类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ProcessHistoryModel extends LoadableDetachableModel<HistoricProcessInstance>{

    private transient HistoryService historyService =(HistoryService) OsgiService.getService(HistoryService.class.getName());;

    private String processDefinitionId;

    public ProcessHistoryModel() {
    }

    public ProcessHistoryModel(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    protected HistoricProcessInstance load() {
        if(processDefinitionId == null)
            return null;
        return historyService.createHistoricProcessInstanceQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public void setObject(HistoricProcessInstance object) {
        super.setObject(object);
        if(object != null)
            processDefinitionId = object.getId();
    }
}
