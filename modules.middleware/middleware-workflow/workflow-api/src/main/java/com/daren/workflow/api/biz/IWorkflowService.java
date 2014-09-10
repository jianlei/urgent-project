package com.daren.workflow.api.biz;

/**
 * @类描述：Workflow业务服务接口类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/9
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public interface IWorkflowService  {

    void findAllProcessDeployTimes();

    void findAllProcessDefinitions();

    void findAllLastVesions();


    void deleteProcessDefinitionById();

    void getProcessDefinitionContent() throws Exception;

    void getProcessDefinitionContentPng() throws Exception;

    void createProcessInstance();

    void findAllTasks();

    void findPersonalTasks();

    void findCandidateUserTasks();

    void findCandidateGroupTasks();

    void findDateRangeTasks();

    void claimTask();

    void handldTask();

    void getVariableByTaskInstanceId();

    void getAllExecuteTaskNodeList();

    void findExecutionList();

    void findProcessInstanceList();
}
