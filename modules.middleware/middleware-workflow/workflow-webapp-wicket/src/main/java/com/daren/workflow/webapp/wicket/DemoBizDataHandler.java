package com.daren.workflow.webapp.wicket;

import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.workflow.webapp.wicket.page.DemoBizDataPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class DemoBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "test";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new DemoBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return "demoData";
    }
}
