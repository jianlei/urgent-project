
package com.daren.core.web.api.workflow;


import org.apache.wicket.markup.html.panel.Panel;

/**
 * 实现工作流业务数据的统一接口
 */
public interface IBizDataHandler {
    /**
     * 返回业务数据的描述
     * @return
     */
    String getBizName();

    /**
     * 获得form的实现panel
     * @param id
     * @param bizId  业务实体的id
     * @return
     */
    Panel getPanel(String id, String bizId);

    /**
     * 获得流程定义名称
     * @return
     */
    String getProcessDefinitionId();

}
