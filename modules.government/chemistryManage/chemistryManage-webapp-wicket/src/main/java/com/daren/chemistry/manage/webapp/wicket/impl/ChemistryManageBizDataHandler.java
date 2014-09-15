package com.daren.chemistry.manage.webapp.wicket.impl;

import com.daren.chemistry.manage.webapp.wicket.Const;
import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageBizDataPanel;
import com.daren.core.web.api.workflow.IBizDataHandler;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：业务数据的实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "危险化学品经营许可证";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new ChemistryManageBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
