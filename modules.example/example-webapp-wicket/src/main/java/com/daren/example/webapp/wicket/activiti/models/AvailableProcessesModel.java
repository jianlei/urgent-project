package com.daren.example.webapp.wicket.activiti.models;

import com.daren.example.webapp.wicket.activiti.util.OsgiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */

public class AvailableProcessesModel extends LoadableDetachableModel<List<ProcessDefinition>>{

	private transient RepositoryService repositoryService;

	public AvailableProcessesModel() {
		super();
	}

	@Override
	protected List<ProcessDefinition> load() {
        repositoryService= (RepositoryService) OsgiService.getService(RepositoryService.class.getName());
        return repositoryService.createProcessDefinitionQuery().latestVersion().list();
	}
}
