package com.daren.core.web.wicket.manager;

import com.daren.core.web.api.workflow.IBizDataHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @类描述：业务数据管理类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class BizDataPanelManager {
    private static BizDataPanelManager install;

    //业务数据map
    private Map<String, IBizDataHandler> bizDataMap = new HashMap<>();

    private BizDataPanelManager() {

    }
    public synchronized static BizDataPanelManager getInstall() {
        if (install == null) {
            install = new BizDataPanelManager();
        }
        return install;
    }

    /**
     * 根据ProcessDefinitionId添加
     *
     * @param bizDataHandler
     */
    public void put(IBizDataHandler bizDataHandler) {
        if(!bizDataMap.containsKey(bizDataHandler.getProcessDefinitionId())){
            bizDataMap.put(bizDataHandler.getProcessDefinitionId(),bizDataHandler);
        }
    }

    /**
     * 返回panel根据processDefinitionId
     * @param processDefinitionId
     * @return
     */
    public IBizDataHandler findPanelByKey(String processDefinitionId){
        return bizDataMap.get(processDefinitionId);
    }

    /**
     *  根据ProcessDefinitionId remove
     * @param bizDataHandler
     */
    public void remove(IBizDataHandler bizDataHandler) {
        if(bizDataMap.containsKey(bizDataHandler.getProcessDefinitionId())){
            bizDataMap.remove(bizDataHandler.getProcessDefinitionId());
        }
    }
}
