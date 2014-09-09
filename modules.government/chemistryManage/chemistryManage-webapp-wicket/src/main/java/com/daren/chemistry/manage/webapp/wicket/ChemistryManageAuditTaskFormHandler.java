package com.daren.chemistry.manage.webapp.wicket;

import com.daren.chemistry.manage.webapp.wicket.page.ChemistryManageAuditTaskFormPage;
import com.daren.core.web.api.workflow.IFormHandler;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @类描述：demo流程的审批Taskform注册服务
 * @创建人： sunlingfeng
 * @创建时间：2014/9/6
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class ChemistryManageAuditTaskFormHandler implements IFormHandler{
    @Override
    public Panel getPanel(String id, IModel model) {
        return new ChemistryManageAuditTaskFormPage(id,model);
    }

    @Override
    public String getFormKey() {
        return "audit.form";
    }

    @Override
    public String getProcessDefinitionId() {
        return "chemistryManage";
    }
}
