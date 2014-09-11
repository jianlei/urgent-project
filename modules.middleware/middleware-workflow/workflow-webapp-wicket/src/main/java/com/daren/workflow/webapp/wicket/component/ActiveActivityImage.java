package com.daren.workflow.webapp.wicket.component;

import com.daren.workflow.webapp.wicket.util.OsgiService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.DynamicImageResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @类描述：输出流程图片
 * @创建人： sunlingfeng
 * @创建时间：2014/9/11
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ActiveActivityImage extends NonCachingImage {

    ProcessEngine processEngine=(ProcessEngine) OsgiService.getService(ProcessEngine.class.getName());

    /**
     * 输出流程图片，并标记当前活动节点
     * @param id
     * @param model
     */
    public ActiveActivityImage(String id, Task model) {
        super(id);
        final Task task=model;
        setImageResource(new DynamicImageResource() {

            @Override
            protected byte[] getImageData(Attributes attributes) {
                try {
                    /**流程实例**/
                   BpmnModel bpmnModel = processEngine.getRepositoryService()
                            .getBpmnModel(task.getProcessDefinitionId());
                    List<String> activeActivityIds =  processEngine.getRuntimeService().getActiveActivityIds(task.getProcessInstanceId());
                    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
                    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());
                    /**得到图片输出流**/
                    InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

                    return input2byte(imageStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        });

    }
    /**
     * 输出流程图片
     * @param id
     * @param pd
     */
    public ActiveActivityImage(String id, ProcessDefinition pd) {
        super(id);
        final ProcessDefinition processDefinition=pd;
        setImageResource(new DynamicImageResource() {

            @Override
            protected byte[] getImageData(Attributes attributes) {
                try {
                    /**流程实例**/
                    BpmnModel bpmnModel = processEngine.getRepositoryService()
                            .getBpmnModel(processDefinition.getId());
//                    List<String> activeActivityIds =  processEngine.getRuntimeService().getActiveActivityIds(task.getProcessInstanceId());
                    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
                    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());
                    /**得到图片输出流**/
                    InputStream imageStream = ProcessDiagramGenerator.generatePngDiagram(bpmnModel);
                    return input2byte(imageStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new byte[0];
            }
        });

    }

    /**
     * input流转换为byte
     * @param inStream
     * @return
     * @throws IOException
     */
    private final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

}
