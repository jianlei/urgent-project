package com.daren.production.webapp.wicket.impl;

import com.daren.core.web.api.workflow.IFormHandler;
import com.daren.production.webapp.wicket.Const;
import com.daren.production.webapp.wicket.page.ProductionAuditFormPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：安全生产许可证(非煤矿矿山企业)
 * @创建人： Administrator
 * @创建时间：2014/9/13
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ProductionAuditFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ProductionAuditFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "audit.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return Const.PROCESS_KEY;
    }
}
