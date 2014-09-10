package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.wicket.model.LoadableDetachableModel;

@SuppressWarnings("serial")
public class TaskModel extends LoadableDetachableModel<Task> {

	private transient TaskService taskService=(TaskService) OsgiService.getService(TaskService.class.getName());

    private String taskId;

    public TaskModel() {
        super();
    }

	public TaskModel(String taskId) {
		super();
		this.taskId = taskId;
	}

	@Override
	protected Task load() {
		if (taskId == null)
			return null;
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

    @Override
    public void setObject(Task object) {
        super.setObject(object);
        if(object != null)
            taskId = object.getId();
    }
}
