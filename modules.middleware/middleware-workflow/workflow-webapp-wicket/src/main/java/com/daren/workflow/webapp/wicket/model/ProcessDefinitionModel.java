package com.daren.workflow.webapp.wicket.model;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Created by IntelliJ IDEA.
 * User: jmader
 * Date: 15.09.11
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */

public class ProcessDefinitionModel extends LoadableDetachableModel<ProcessDefinition>{

    private transient RepositoryService repositoryService=(RepositoryService) OsgiService.getService(RepositoryService.class.getName());;

    private String processDefinitionId;

    public ProcessDefinitionModel() {
    }

    public ProcessDefinitionModel(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    protected ProcessDefinition load() {
        if(processDefinitionId == null)
            return null;
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public void setObject(ProcessDefinition object) {
        super.setObject(object);
        if(object != null)
            processDefinitionId = object.getId();
    }
}
