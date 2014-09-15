package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */

public class ProcessesHistoryListModel extends LoadableDetachableModel<List<HistoricProcessInstance>>{

	private transient HistoryService historyService;

	public ProcessesHistoryListModel() {
		super();
	}

	@Override
	protected List<HistoricProcessInstance> load() {

        historyService= (HistoryService) OsgiService.getService(HistoryService.class.getName());
        return historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceStartTime().desc().list();
	}
}
