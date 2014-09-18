package com.daren.fireworks.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.fireworks.webapp.wicket.Const;
import com.daren.fireworks.webapp.wicket.page.FireworksBizDataPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：业务数据的实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class FireworksBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "烟花爆竹经营(批发)许可证";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new FireworksBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
