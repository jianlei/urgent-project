package com.daren.core.web.wicket.listener;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.core.web.wicket.manager.FormPanelManager;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-1-24
 * Time: 下午6:17
 * Form监听者
 */
public class FormPanelListener {
    private static Logger logger = Logger.getLogger(FormPanelListener.class);

    /**
     * 监听到Form
     *
     * @param formHandler
     */
    public void bind(IFormHandler formHandler) {
        logger.info("workflow " + formHandler.getProcessDefinitionId() + " is binded!");
        FormPanelManager.getInstall().add(formHandler);

    }


    /**
     * Form被移除
     *
     * @param formHandler
     */
    public void unbind(IFormHandler formHandler) {
        logger.info("Workflow of " + formHandler.getProcessDefinitionId() + " is unbound!");
        FormPanelManager.getInstall().remove(formHandler);
    }

}
