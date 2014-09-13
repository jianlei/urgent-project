package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

@SuppressWarnings("serial")
public class TasksAssignableToUserModel extends LoadableDetachableModel<List<Task>> {
	private final IModel<String> userIdModel;

	private transient TaskService taskService=(TaskService) OsgiService.getService(TaskService.class.getName());;

	public TasksAssignableToUserModel(IModel<String> userIdModel) {
		this.userIdModel = userIdModel;
	}

	@Override
	protected List<Task> load() {
		return taskService
				.createTaskQuery()
				//todo sunlf
				 .taskCandidateUser(userIdModel.getObject())
                .list();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		userIdModel.detach();
	}
	
	
}