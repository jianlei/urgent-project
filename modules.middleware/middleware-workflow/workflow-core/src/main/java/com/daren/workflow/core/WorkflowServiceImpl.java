package com.daren.workflow.core;

import com.daren.workflow.api.biz.IWorkflowService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类描述：Workflow业务服务实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/10
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class WorkflowServiceImpl implements IWorkflowService {
    /*public static void main(String[] args) {
  //获取activiti引擎
  ProcessEngine processEngine = null ;
  processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("DataAccessContext.xml","processEngineConfiguration").buildProcessEngine() ;

  RepositoryService repositoryService = processEngine.getRepositoryService();
  repositoryService.createDeployment()
.addClasspathResource("demo01/Interview.bpmn20.xml")
.deploy();
 }*/
    private static ProcessEngine processEngine;

    /**
     *  *
     *  * Function : 加载processEngine
     *  * @author : Liaokailin
     *  * @date : 2014-1-15
     *  
     */
    public static void beforeClass() {
        if (processEngine == null)
            System.out.println("----加载processEngine---");
        processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("DataAccessContext.xml", "processEngineConfiguration").buildProcessEngine();
    }

    /**
     * Function :部署流程定义
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("demo01/Interview.bpmn20.xml").deploy();

    }

    /**
     * Function : 查询所有流程发布时间
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findAllProcessDeployTimes() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取流程发布时间 发布id对于ACT_RE_DEPLOYMENT表
        //List<Deployment> list = repositoryService.createDeploymentQuery().list(); //查询所有
        //long count = repositoryService.createDeploymentQuery().count();//数据长度
        //System.out.println("数据长度:"+count);
/**
 * orderByDeploymentId :按照id_排序
 * asc：升序
 * listPage：分页查询 0:表示起始位置，4：表示查询长度
 */
        List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymentId().asc().listPage(0, 4);
        for (Deployment d : list) {
            System.out.println("id:" + d.getId() + ",name:" + d.getName() + ",time:" + d.getDeploymentTime());
        }
    }

    /**
     * 查询所有流程定义
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findAllProcessDefinitions() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
//对应数据表 ACT_RE_PROCDEF
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().asc().list();
        for (ProcessDefinition p : list) {
            System.out.println(p.getDeploymentId() + "," + p.getName());
        }
    }

    /**
     * 查询所有流程的最新版本列表
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findAllLastVesions() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
//对应数据表 ACT_RE_PROCDEF
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        for (ProcessDefinition p : list) {
            System.out.println(p.getDeploymentId() + "," + p.getName() + ",version:" + p.getVersion());
        }
    }


    /**
     * 删除流程
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */


    @Override
    public void deleteProcessDefinitionById() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
/**
 * 传递的参数为：deployment_id的值
 */
        repositoryService.deleteDeployment("1001");
    }

    /**
     * 获取流程定义文档中的文件内容(xml 打印出来)
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void getProcessDefinitionContent() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream is = repositoryService.getResourceAsStream("1101", "demo01/Interview.bpmn20.xml");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        System.out.println(new String(bos.toByteArray()));
        bos.close();

    }


    /**
     * 获取流程定义文档中的文件内容(png输出到文件 会乱码activiti新版本通过set方法设置字体 老版本修改源码)
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void getProcessDefinitionContentPng() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream is = repositoryService.getResourceAsStream("1101", "demo01/Interview.Interview.png");
        File file = new File("d:" + File.separator + "test.png");
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        fos.flush();
        fos.close();
    }

        /*--------------------------Process执行----------------------------*/

    /**
     * 1. 创建流程实例 
     * 流程实例创建以后开始创建task对于数据表ACT_RU_TASK 保存task信息
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void createProcessInstance() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //TODO这里可以设置流程变量 （Map集合）
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("Interview:7:604");
        System.out.println("创建流程实例:\n\t 流程实例ID:" + processInstance.getId() + ",业务关联ID：" + processInstance.getBusinessKey() + ",流程定义对应的主键ID：" + processInstance.getProcessDefinitionId() + ",流程实例ID" + processInstance.getProcessInstanceId());
    }

    /**
     * 2.1查询所有的任务 TODO设置为是当前流程实例中的任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findAllTasks() {
        System.out.println("processEngine.getName():" + processEngine.getName());
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().list();
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState());
        }
    }

    /**
     * 2.2获取指定人对应的任务 （只有该人才能查询到） TODO设置为是当前流程实例中的任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findPersonalTasks() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
        System.out.println(list.size());
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState());
        }
    }

    /**
     * 2.3获取指定候选人对应的任务配置为 activiti:candidateUsers="名称" TODO设置为是当前流程实例中的任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findCandidateUserTasks() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskCandidateUser("张三").list();
        System.out.println(list.size());
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState());
        }
    }


    /**
     * 2.4获取指定候选人对应的任务配置为activiti:candidateGroups="用户组" TODO设置为是当前流程实例中的任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findCandidateGroupTasks() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup("技术部").list();
        System.out.println(list.size());
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState() + ",name=" + e.getName());
        }
    }


    /**
     * 2.5获取指定时间区间对应的任务TODO设置为是当前流程实例中的任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findDateRangeTasks() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskCreatedAfter(new Date()).list();//taskCreatedAfter()
        System.out.println(list.size());
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState() + ",name=" + e.getName());
        }
    }

    /**
     * 3. 获取任务 分配任务(必须是当前流程实例中的任务)任务一旦分配给一人以后则其他用户不可查询到
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void claimTask() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup("技术部").processInstanceId("1201").list();
        System.out.println(list.size());
        for (Task e : list) {
            Map map = taskService.getVariables(e.getId());
            if (map != null) {
                System.out.println(( map.get("vehicle")));
            }
            //将任务指派给张三
            taskService.claim(e.getId(), "张三");
            //System.out.println(e.getId()+","+e.getProcessInstanceId()+","+e.getAssignee()+","+e.getExecutionId()+","+e.getDelegationState()+",name="+e.getName());
        }
    }

    /**
     * 办理任务
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void handldTask() {

        TaskService taskService = processEngine.getTaskService(); //活动task服务
        List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
        System.out.println(list.size());
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", "liaokailin");
        map.put("remark", "赠送一辆小轿车");
        for (Task e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + "," + e.getAssignee() + "," + e.getExecutionId() + "," + e.getDelegationState());
            taskService.complete(e.getId(), map);
        }


    }

    /**
     * 取得任务实例id对应的流程实例中的流程变量
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void getVariableByTaskInstanceId() {
        TaskService taskService = processEngine.getTaskService(); //活动task服务
        Map map = taskService.getVariables("1306");
//        System.out.println(((TmVehiclePO) map.get("vehicle")).getVin());
    }

    /**
     *  获取执行过的所有任务节点的列表必须指定流程实例才精确考虑如何获取指定流程定义中的所有节点
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void getAllExecuteTaskNodeList() {
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("1201").list();
//  List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("1201").list();
        System.out.println(list.size());
        for (HistoricTaskInstance h : list) {
            System.out.println(h.getName());
        }
        // Node node = null ;
//taskService.getIdentityLinksForTask(taskId) //获取ACT_RU_IDENTITYLINK中的信息
    }

    /**
     * 附加操作：查询Execution操作列表
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findExecutionList() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<Execution> list = runtimeService.createExecutionQuery().list();
        for (Execution e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId());
        }
    }

    /**
     * 附加操作：查询流程实例列表
     * Function :
     *
     * @author : Liaokailin
     * @date : 2014-1-15
     */

    @Override
    public void findProcessInstanceList() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance e : list) {
            System.out.println(e.getId() + "," + e.getProcessInstanceId() + ",该实例对应的流程定义" + e.getProcessDefinitionId() + ",业务外键：" + e.getBusinessKey());
        }
    }
}
