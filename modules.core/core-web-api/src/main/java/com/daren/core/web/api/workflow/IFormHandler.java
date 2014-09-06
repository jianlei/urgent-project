
package com.daren.core.web.api.workflow;


import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * 实现工作流form的统一接口
 */
public interface IFormHandler {
    /**
     * 获得form的实现panel
     * @param id
     * @param model
     * @return
     */
    Panel getPanel(String id, IModel model);

    /**
     * 获得form的key
     * @return
     */
    String getFormKey();

    /**
     * 获得流程定义名称
     * @return
     */
    String getProcessDefinitionId();

}
