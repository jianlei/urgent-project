package com.daren.competency.webapp.wicket.impl;

import com.daren.competency.webapp.wicket.Const;
import com.daren.competency.webapp.wicket.page.CompetencyBizDataPanel;
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
public class CompetencyBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "安全资格证书(培训)";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new CompetencyBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
