package com.daren.government.webapp.wicket.model;

import com.daren.government.webapp.wicket.util.OsgiService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;


@SuppressWarnings("serial")
public class TasksNotAssignableToUserModel extends
		LoadableDetachableModel<List<Task>> {
	private final IModel<String> roleModel;

	private transient TaskService taskService=(TaskService) OsgiService.getService(TaskService.class.getName());;

	public TasksNotAssignableToUserModel(IModel<String> roleModel) {
		this.roleModel = roleModel;
	}

	@Override
	protected List<Task> load() {
		return taskService
				.createTaskQuery()
				.taskCandidateGroup(roleModel.getObject())
                .list();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		roleModel.detach();
	}

}