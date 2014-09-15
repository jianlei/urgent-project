package com.daren.core.web.wicket.listener;

import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.core.web.wicket.manager.BizDataPanelManager;
import org.apache.log4j.Logger;
/**
 * @类描述：业务数据监听者
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class BizDataPanelListener {
    private static Logger logger = Logger.getLogger(BizDataPanelListener.class);

    /**
     * 监听到BizData
     *
     * @param bizDataHandler
     */
    public void bind(IBizDataHandler bizDataHandler) {
        logger.info("workflow bizData" + bizDataHandler.getProcessDefinitionId() + " is binded!");
        BizDataPanelManager.getInstall().put(bizDataHandler);

    }


    /**
     * Form被移除
     *
     * @param bizDataHandler
     */
    public void unbind(IBizDataHandler bizDataHandler) {
        logger.info("workflow bizData " + bizDataHandler.getProcessDefinitionId() + " is unbound!");
        BizDataPanelManager.getInstall().remove(bizDataHandler);
    }

}
