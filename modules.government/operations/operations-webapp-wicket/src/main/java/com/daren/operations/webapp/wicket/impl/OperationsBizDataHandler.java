package com.daren.operations.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.operations.webapp.wicket.Const;
import com.daren.operations.webapp.wicket.page.OperationsBizDataPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：业务数据的实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class OperationsBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "特种作业人员操作资格证";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new OperationsBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
