package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 * @类描述：我的申请历史列表模型类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/11
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class ProcessesMyApplyHistoryListModel extends LoadableDetachableModel<List<HistoricProcessInstance>>{

	private transient HistoryService historyService;

    private String startUserName;

	public ProcessesMyApplyHistoryListModel() {
		super();
	}

    public ProcessesMyApplyHistoryListModel(String startUserName) {
        super();
        this.startUserName=startUserName;
    }


	@Override
	protected List<HistoricProcessInstance> load() {
        if(startUserName == null)
            return null;
        historyService= (HistoryService) OsgiService.getService(HistoryService.class.getName());
        return historyService.createHistoricProcessInstanceQuery().startedBy(startUserName).orderByProcessInstanceStartTime().desc().list();
	}
}
