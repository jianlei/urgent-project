package com.daren.production.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IBizDataHandler;
import com.daren.production.webapp.wicket.Const;
import com.daren.production.webapp.wicket.page.ProductionBizDataPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @类描述：业务数据的实现类
 * @创建人： sunlingfeng
 * @创建时间：2014/9/12
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProductionBizDataHandler implements IBizDataHandler {
    @Override
    public String getBizName() {
        return "安全生产许可证(非煤矿矿山企业)";
    }

    @Override
    public Panel getPanel(String id, String bizId) {
        return new ProductionBizDataPanel(id,bizId);
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
